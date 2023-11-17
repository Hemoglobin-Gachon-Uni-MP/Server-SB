package com.mp.PLine.src.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.myPage.dto.res.GetCertificationRes;
import com.mp.PLine.src.myPage.dto.res.GetRewardRes;
import com.mp.PLine.src.myPage.dto.req.PostCertificationReq;
import com.mp.PLine.src.myPage.dto.res.GetMemberRes;
import com.mp.PLine.src.myPage.dto.req.PatchMemberReq;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
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
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
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

    /**
     * Upload Donation API
     * [GET] /mypages/certification
     */
    @ApiOperation("헌혈 인증 업로드 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", dataTypeClass = Integer.class, paramType = "formData", value = "image"),
            @ApiImplicitParam(name = "certification", dataTypeClass = PostCertificationReq.class, paramType = "formData", value = "PostCertificationReq", type = "application/json"),
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2050, message = "이름을 입력해 주세요."),
            @ApiResponse(code = 2051, message = "헌혈 번호를 입력해주세요."),
            @ApiResponse(code = 2052, message = "헌혈 날짜를 입력해주세요."),
            @ApiResponse(code = 2053, message = "헌혈 증서를 업로드해주세요."),
            @ApiResponse(code = 2054, message = "본인이 한 헌혈 증서만 등록 가능합니다.")
    })
    @ResponseBody
    @PostMapping(value = "/certification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<Long> uploadDonation(HttpServletRequest request, @RequestPart(value = "image", required = false) MultipartFile image,
                                             @RequestPart(value = "certification") PostCertificationReq postCertificationReq) {
        try {
            // blank & form check
            BaseResponseStatus status = Validation.checkCertification(postCertificationReq);
            if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(myPageService.uploadDonation(memberId, image, postCertificationReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (IOException e) {
            return new BaseResponse<>(BaseResponseStatus.POST_CERTIFICATION_EMPTY_IMAGE);
        }
    }

    /**
     * Return Certification list API
     * [GET] /mypages/certification/list
     */
    @ApiOperation("헌혈 목록 반환 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
    })
    @ResponseBody
    @GetMapping("/certification/list")
    public BaseResponse<List<GetCertificationRes>> getCertificationList() {
        // 이름 날짜 증서번호
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(myPageService.getCertificationList(memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Return Certification Reward List API
     * [GET] /mypages/certification/reward-list
     */
    @ApiOperation("헌혈 리워드 반환 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
    })
    @ResponseBody
    @GetMapping("/certification/reward-list")
    public BaseResponse<GetRewardRes> getRewardList() {
        // 이름 날짜 증서번호
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(myPageService.getRewardList(memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
