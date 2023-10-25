package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.src.admin.entity.Certification;
import com.mp.PLine.src.feed.dto.res.GetFeedsRes;
import com.mp.PLine.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "관리자 앱")
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;
    private final JwtService jwtService;

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
