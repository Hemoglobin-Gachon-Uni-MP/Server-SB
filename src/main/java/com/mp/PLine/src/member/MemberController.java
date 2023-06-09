package com.mp.PLine.src.member;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.member.dto.req.PostUserReq;
import com.mp.PLine.src.member.dto.res.PostUserRes;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import com.mp.PLine.utils.entity.Status;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Api(tags = "회원가입 및 로그인")
@RequestMapping("")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    /**
     * Get kakao AccessToken API
     * using if there is no client
     */
    @ApiIgnore
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        String token = memberService.getKaKaoAccessToken(code);
        memberService.createKakaoUser(token);
    }

    /**
     * Sign up with kakao API
     * Get kakaoId using AccessToken sent by client
     * [POST} /kakao/sign-up
     */
    @ApiOperation("카카오 회원가입 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "K-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2004, message = "ACCESS TOKEN을 입력해주세요."),
            @ApiResponse(code = 2010, message = "이름을 입력해주세요."),
            @ApiResponse(code = 2011, message = "닉네임을 입력해주세요."),
            @ApiResponse(code = 2012, message = "생년월일을 입력해주세요."),
            @ApiResponse(code = 2013, message = "전화번호를 입력해주세요."),
            @ApiResponse(code = 2014, message = "성별을 입력해주세요."),
            @ApiResponse(code = 2015, message = "위치를 입력해주세요."),
            @ApiResponse(code = 2020, message = "닉네임 길이를 확인해주세요."),
            @ApiResponse(code = 2021, message = "올바르지 않은 전화번호 형식입니다."),
            @ApiResponse(code = 2022, message = "올바르지 않은 생년월일 형식입니다."),
            @ApiResponse(code = 2023, message = "올바르지 않은 성별 형식입니다."),
            @ApiResponse(code = 2024, message = "올바르지 않은 ABO 혈액형 형식입니다."),
            @ApiResponse(code = 2025, message = "올바르지 않은 RH 혈액형 형식입니다."),
            @ApiResponse(code = 2026, message = "올바르지 않은 프로필 사진 형식입니다."),
            @ApiResponse(code = 2027, message = "이미 존재하는 유저입니다.")
    })
    @PostMapping("/kakao/sign-up")
    public BaseResponse<PostUserRes> signUp(@RequestBody PostUserReq postUserReq) {
        // blank & form check
        BaseResponseStatus status = Validation.checkSignUp(postUserReq);
        if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

        try {
            // get Kakao AccessToken
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String accessToken = request.getHeader("K-ACCESS-TOKEN");

            if(accessToken.isBlank()) return new BaseResponse<>(BaseResponseStatus.EMPTY_ACCESS_TOKEN);

            // create user
            Long kakaoId = memberService.createKakaoUser(accessToken);
            Long age = 2023 - Long.parseLong(postUserReq.getBirth().substring(0, 4)) + 1;

            Long userId = memberService.signUp(postUserReq, kakaoId, age);
            String jwt = jwtService.createJwt(userId);

            return new BaseResponse<>(new PostUserRes(jwt, userId));

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

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
            @ApiResponse(code = 2004, message = "ACCESS TOKEN을 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @PostMapping("/kakao/sign-in")
    public BaseResponse<PostUserRes> signIn() {
        // get Kakao AccessToken
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("K-ACCESS-TOKEN");

        if (accessToken.isEmpty()) return new BaseResponse<>(BaseResponseStatus.EMPTY_ACCESS_TOKEN);

        // get kakaoId
        Long kakaoId = memberService.createKakaoUser(accessToken);

        // check user existence
        Long userId;
        Optional<Member> member = memberRepository.findByKakaoIdAndStatus(kakaoId, Status.A);

        // if user exist, return userId and jwt
        if (member.isPresent()) {
            userId = member.get().getId();
            String jwt = jwtService.createJwt(userId);
            return new BaseResponse<>(new PostUserRes(jwt, userId));
        } else {
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER);
        }
    }

    /**
     * Resign API
     * [PATCH] /{userId}/status
     */
    @ApiOperation("회원 탈퇴 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
    })
    @PatchMapping("/resign/{userId}")
    public BaseResponse<String> resign(@PathVariable Long userId) {
        try {
            // get jwt from header
            Long userIdByJwt = jwtService.getUserId();
            if(!userId.equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            return new BaseResponse<>(memberService.resign(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
