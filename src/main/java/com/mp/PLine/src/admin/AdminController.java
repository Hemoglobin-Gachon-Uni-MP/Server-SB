package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.admin.dto.request.AdminRequestDto;
import com.mp.PLine.src.admin.dto.response.AdminResponseDto;
import com.mp.PLine.src.feed.dto.res.GetFeedRes;
import com.mp.PLine.src.report.dto.CertificationResponseDto;
import com.mp.PLine.src.report.dto.ReportRequestDto;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "관리자 앱")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @ApiOperation("어드민 신청 API")
    @PostMapping("/accounts/sign-up")
    public BaseResponse<BaseResponseStatus> adminSignUp(@RequestBody AdminRequestDto.LoginDto adminRequest) throws BaseException {
        adminService.signUp(adminRequest);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @ApiOperation("어드민 로그인 API")
    @PostMapping("/accounts/login")
    public BaseResponse<AdminResponseDto.ResponseDto> adminLogin(@RequestBody AdminRequestDto.LoginDto adminRequest) throws BaseException {
        return new BaseResponse<>(adminService.login(adminRequest));
    }
    
    @ApiOperation("신고글 조회 API")
    @GetMapping("/reports")
    public BaseResponse<List<ReportResponseDto>> readReports() {
        return new BaseResponse<>(adminService.readReports());
    }

    @ApiOperation("신고 게시물 상세 조회 API")
    @GetMapping("/reports/detail")
    public BaseResponse<GetFeedRes> readDetailReport(@RequestParam Long reportId,
                                                     @RequestParam String category,
                                                     @RequestParam Long feedOrCommentId) throws BaseException {
        return new BaseResponse<>(adminService.readDetailReport(reportId, category, feedOrCommentId));
    }

    @ApiOperation("신고 게시물 삭제 및 처리 API")
    @PutMapping("/reports/feeds/execute")
    public BaseResponse<BaseResponseStatus> executeFeedsReport(@RequestBody ReportRequestDto.FeedReportDto reportDto) throws BaseException {
        adminService.executeFeedReport(reportDto);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @ApiOperation("신고 (대)댓글 삭제 및 처리 API")
    @PutMapping("/reports/comments/execute")
    public BaseResponse<BaseResponseStatus> executeCommentsReport(@RequestBody ReportRequestDto.CommentReportDto reportDto) throws BaseException {
        adminService.executeCommentsReport(reportDto);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @ApiOperation("신고 반려 API")
    @PutMapping("/reports/execute")
    public BaseResponse<BaseResponseStatus> rejectReport(@RequestParam Long id) throws BaseException {
        adminService.rejectReport(id);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @ApiOperation("인증글 조회 API")
    @GetMapping("/certifications")
    public BaseResponse<List<CertificationResponseDto>> readCertifications() {
        return new BaseResponse<>(adminService.readCertifications());
    }

    @ApiOperation("인증 수락 API")
    @PutMapping("/certifications/execute")
    public BaseResponse<BaseResponseStatus> executeCertification(@RequestParam Long id) throws BaseException {
        adminService.executeCertification(id);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @ApiOperation("인증 반려 API")
    @PutMapping("/certifications/reject")
    public BaseResponse<BaseResponseStatus> rejectCertification(@RequestParam Long id) throws BaseException {
        adminService.rejectCertification(id);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}
