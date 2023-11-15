package com.mp.PLine.src.feed;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.req.PatchFeedReq;
import com.mp.PLine.src.feed.dto.req.PostCommentReq;
import com.mp.PLine.src.feed.dto.req.PostFeedReq;
import com.mp.PLine.src.feed.dto.req.PostReplyReq;
import com.mp.PLine.src.feed.dto.res.GetFeedRes;
import com.mp.PLine.src.feed.dto.res.GetFeedsRes;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "홈 - 피드")
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;
    private final JwtService jwtService;

    /**
     * Create feed API
     * [POST] /feeds
     */
    @ApiOperation("게시물 생성 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2031, message = "본문을 입력해주세요."),
            @ApiResponse(code = 2032, message = "장소를 입력해주세요."),
            @ApiResponse(code = 2033, message = "수혈 & 공혈 여부를 입력해주세요."),
            @ApiResponse(code = 2040, message = "올바르지 않은 ABO 혈액형 형식입니다."),
            @ApiResponse(code = 2041, message = "올바르지 않은 RH 혈액형 형식입니다."),
            @ApiResponse(code = 2042, message = "올바르지 않은 수혈 & 공혈 형식입니다.")
    })
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Long> postFeed(@RequestBody PostFeedReq postFeedReq) {
        try {
            // blank & form check
            BaseResponseStatus status = Validation.checkPostFeed(postFeedReq);
            if (status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            // JWT 추출
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.postFeed(postFeedReq, memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Return feed list API
     * [GET] /feeds/info-list
     */
    @ApiOperation("게시물 목록 반환 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.")
    })
    @ResponseBody
    @GetMapping("info-list")
    public BaseResponse<List<GetFeedsRes>> getFeeds() {
        try {
            return new BaseResponse<>(feedService.getFeeds());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Return feed detail API
     * [GET] /feeds/info/{feedId}
     */
    @ApiOperation("게시물 정보 반환 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다.")
    })
    @ResponseBody
    @GetMapping("/info/{feedId}")
    public BaseResponse<GetFeedRes> getFeed(@PathVariable Long feedId) {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.getFeed(memberId, feedId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Edit feed API
     * [PATCH] /feeds/{feedId}
     */
    @ApiOperation("게시물 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
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
            // blank & form check
            BaseResponseStatus status = Validation.checkUpdateFeed(patchFeedReq);
            if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.updateFeed(feedId, memberId, patchFeedReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Delete feed API
     * [PATCH] /feeds/{feedId}/status
     */
    @ApiOperation("게시물 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
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
    public BaseResponse<String> deleteFeed(@PathVariable Long feedId) {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.deleteFeed(feedId, memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Create comment API
     * [POST] /feeds/comment/{feedId}
     */
    @ApiOperation("댓글 달기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2031, message = "본문을 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다.")
    })
    @PostMapping("/comment/{feedId}")
    public BaseResponse<Long> postComment(@PathVariable Long feedId, @RequestBody PostCommentReq postCommentReq) {
        // blank & form check
        BaseResponseStatus status = Validation.checkPostComment(postCommentReq);
        if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.postComment(feedId, memberId, postCommentReq.getContext()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Delete comment API
     * [PATCH] /feeds/comment/{commentId}/status
     */
    @ApiOperation("댓글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2046, message = "존재하지 않는 댓글입니다.")
    })
    @PatchMapping("/comment/{commentId}/status")
    public BaseResponse<String> postComment(@PathVariable Long commentId) {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.deleteComment(commentId, memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Create reply API
     * [POST] /feeds/reply/{commentId}
     */
    @ApiOperation("답글 달기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2031, message = "본문을 입력해주세요."),
            @ApiResponse(code = 2034, message = "게시물 아이디를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2043, message = "올바르지 않은 게시물 경로입니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다."),
            @ApiResponse(code = 2046, message = "존재하지 않는 댓글입니다.")

    })
    @PostMapping("/reply/{commentId}")
    public BaseResponse<Long> postReply(@PathVariable Long commentId, @RequestBody PostReplyReq postReplyReq) {
        // blank & form check
        BaseResponseStatus status = Validation.checkPostReply(postReplyReq);
        if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.postReply(commentId, memberId, postReplyReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Delete reply API
     * [PATCH] /feeds/reply/{replyId}/status
     */
    @ApiOperation("답글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2030, message = "유저 아이디를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2046, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2047, message = "존재하지 않는 답글입니다.")
    })
    @PatchMapping("/reply/{replyId}/status")
    public BaseResponse<String> postReply(@PathVariable Long replyId) {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(feedService.deleteReply(replyId, memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
