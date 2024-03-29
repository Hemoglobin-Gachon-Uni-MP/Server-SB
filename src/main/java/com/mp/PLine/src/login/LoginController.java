package com.mp.PLine.src.login;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.src.login.dto.LoginRequestDto;
import com.mp.PLine.src.member.MemberService;
import com.mp.PLine.src.member.dto.req.PostMemberReq;
import com.mp.PLine.src.member.dto.res.PostMemberRes;
import com.mp.PLine.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation("로그인 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @PostMapping("/accounts/login")
    public BaseResponse<PostMemberRes> login(@RequestBody LoginRequestDto loginDto) throws BaseException {
        return memberService.findMember(loginDto);
    }
    @ApiOperation("회원가입 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @PostMapping("/accounts/sign-up/kakao")
    public BaseResponse<PostMemberRes> signUp(@RequestBody PostMemberReq postMemberReq) throws BaseException {
        return new BaseResponse<>(memberService.signUp(postMemberReq));
    }

}
