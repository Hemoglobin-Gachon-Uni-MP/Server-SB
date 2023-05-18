package com.mp.PLine.src.feed;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.BaseUserIdReq;
import com.mp.PLine.src.feed.dto.PatchFeedReq;
import com.mp.PLine.src.feed.dto.PostCommentReq;
import com.mp.PLine.src.feed.dto.PostFeedReq;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "홈 - 피드")
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;
    private final FeedRepository feedRepository;
    private final JwtService jwtService;

    /**
     * 게시물 업로드 API
     * [POST] /feeds
     */
    @ApiOperation("게시물 업로드 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2031, message = "본문을 입력해주세요."),
            @ApiResponse(code = 2032, message = "장소를 입력해주세요."),
            @ApiResponse(code = 2033, message = "수혈 & 공혈 여부 입력해주세요."),
            @ApiResponse(code = 2040, message = "올바르지 않은 ABO 혈액형 형식입니다."),
            @ApiResponse(code = 2041, message = "올바르지 않은 RH 혈액형 형식입니다."),
            @ApiResponse(code = 2042, message = "올바르지 않은 수혈 & 공혈 형식입니다.")
    })
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Long> postFeed(@RequestBody PostFeedReq postFeedReq) {
        try {
            // 빈 칸 & 형식 검사
            BaseResponseStatus status = Validation.checkPostFeed(postFeedReq);
            if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            // JWT 추출
            Long userIdByJwt = jwtService.getUserId();
            if (!postFeedReq.getUserId().equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            return new BaseResponse<>(feedService.postFeed(postFeedReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 게시물 수정 API
     * [PATCH] /feeds/{feedId}
     */
    @ApiOperation("게시물 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2031, message = "본문을 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다.")
    })
    @ResponseBody
    @PatchMapping("/{feedId}")
    public BaseResponse<String> updateFeed(@PathVariable Long feedId, @RequestBody PatchFeedReq patchFeedReq) {
        try {
            // 빈 칸 & 형식 검사
            BaseResponseStatus status = Validation.checkUpdateFeed(patchFeedReq);
            if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            // JWT 추출
            Long userIdByJwt = jwtService.getUserId();
            if (!patchFeedReq.getUserId().equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            return new BaseResponse<>(feedService.updateFeed(feedId, patchFeedReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 게시물 삭제 API
     * [PATCH] /feeds/{feedId}/status
     */
    @ApiOperation("게시물 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다.")
    })
    @ResponseBody
    @PatchMapping("/{feedId}/status")
    public BaseResponse<String> deleteFeed(@PathVariable Long feedId, @RequestBody BaseUserIdReq baseUserIdReq) {
        try {
            // JWT 추출
            Long userIdByJwt = jwtService.getUserId();
            if (!baseUserIdReq.getUserId().equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            return new BaseResponse<>(feedService.deleteFeed(feedId, baseUserIdReq.getUserId()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 댓글 달기 API
     * [POST] /feeds/{feedId}/comment
     */
    @ApiOperation("댓글 달기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2031, message = "본문을 입력해주세요."),
    })
    @PostMapping("/{feedId}/comment")
    public BaseResponse<Long> postComment(@PathVariable Long feedId, @RequestBody PostCommentReq postCommentReq) {
        // 빈 칸 & 형식 검사
        BaseResponseStatus status = Validation.checkPostComment(postCommentReq);
        if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

        try {
            // JWT 추출
            Long userIdByJwt = jwtService.getUserId();
            if (!postCommentReq.getUserId().equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            return new BaseResponse<>(feedService.postComment(feedId, postCommentReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
