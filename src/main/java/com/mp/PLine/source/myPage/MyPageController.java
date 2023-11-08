package com.mp.PLine.source.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.source.myPage.dto.res.GetMemberRes;
import com.mp.PLine.source.myPage.dto.req.PatchMemberReq;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "마이페이지")
@RequestMapping("/mypages")
public class MyPageController {
    private final MyPageService myPageService;
    private final JwtService jwtService;

    /**
     * Return user information API
     * [GET] /mypages
     */
    @ApiOperation("내 정보 반환 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @GetMapping("")
    public BaseResponse<GetMemberRes> getMember() {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(myPageService.getMember(memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * Edit user API
     * [PATCH] /mypages
     */
    @ApiOperation("내 정보 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2011, message = "닉네임을 입력해주세요."),
            @ApiResponse(code = 2015, message = "위치를 입력해주세요."),
            @ApiResponse(code = 2020, message = "닉네임 길이를 확인해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @PatchMapping("")
    public BaseResponse<String> updateMember(@RequestBody PatchMemberReq patchMemberReq) {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            BaseResponseStatus status = Validation.checkUpdateMember(patchMemberReq);
            if (status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            return new BaseResponse<>(myPageService.updateMember(memberId, patchMemberReq));

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
