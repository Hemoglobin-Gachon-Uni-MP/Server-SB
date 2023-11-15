package com.mp.PLine.src.member;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "회원가입 및 로그인")
@RequestMapping("")
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    /**
     * Resign API
     * [PATCH] /resign
     */
    @ApiOperation("회원 탈퇴 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
    })
    @PatchMapping("/resign")
    public BaseResponse<String> resign() {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(memberService.resign(memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
