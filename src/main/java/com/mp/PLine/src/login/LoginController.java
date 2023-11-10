package com.mp.PLine.src.login;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.login.dto.LoginRequestDto;
import com.mp.PLine.src.member.MemberService;
import com.mp.PLine.src.member.dto.req.PostMemberReq;
import com.mp.PLine.src.member.dto.res.PostMemberRes;
import com.mp.PLine.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
@RestController
public class LoginController {
    private final MemberService memberService;
    private final JwtService jwtService;
        /**
     * Login with kakao API
     * compare kakaoId using AccessToken
     * [POST} /kakao/sign-in
     */
    @ApiOperation("카카오 로그인 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "K-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @PostMapping("/kakao/login")
    public BaseResponse<PostMemberRes> login(@RequestBody LoginRequestDto.LoginDto loginDto) throws BaseException {
        return memberService.findMember(loginDto);
    }

    @PostMapping("/kakao/sign-up")
    public BaseResponse<Object> signUp(@RequestBody PostMemberReq postMemberReq, HttpServletResponse response) throws BaseException {
        jwtService.setAccessTokenHeader(response, memberService.signUp(postMemberReq));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}
