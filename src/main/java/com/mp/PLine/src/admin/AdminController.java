package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.admin.dto.PostCertificationReq;
import com.mp.PLine.src.admin.entity.Certification;
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
@Api(tags = "관리자 앱")
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;
    private final JwtService jwtService;

    /**
     * Upload Donation API
     * [GET] /admins/donation
     */
    @ApiOperation("헌혈 인증 업로드 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", dataTypeClass = Integer.class, paramType = "formData", value = "image"),
            @ApiImplicitParam(name = "certification", dataTypeClass = PostCertificationReq.class, paramType = "body", value = "PostCertificationReq", type = "application/json"),
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2050, message = "이름을 입력해 주세요."),
            @ApiResponse(code = 2051, message = "헌혈 번호를 입력해주세요."),
            @ApiResponse(code = 2052, message = "헌혈 날짜를 입력해주세요."),
            @ApiResponse(code = 2053, message = "헌혈 증서를 업로드해주세요.")
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
            return new BaseResponse<>(adminService.uploadDonation(memberId, image, postCertificationReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (IOException e) {
            return new BaseResponse<>(BaseResponseStatus.POST_CERTIFICATION_EMPTY_IMAGE);
        }
    }

    /**
     * Return Certification list API
     * [GET] /admins/donation-list
     */
    @ApiOperation("게시물 목록 반환 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
    })
    @ResponseBody
    @GetMapping("/donation-list")
    public BaseResponse<List<Certification>> getDonationLists() {
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(adminService.getDonationLists(memberId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
