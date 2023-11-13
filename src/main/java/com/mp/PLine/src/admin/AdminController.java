package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.admin.dto.AdminDto;
import com.mp.PLine.src.report.dto.CertificationResponseDto;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "관리자 앱")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/accounts/sign-up")
    public BaseResponse<BaseResponseStatus> adminSignUp(@RequestBody AdminDto.RequestDto adminRequest) throws BaseException {
        adminService.signUp(adminRequest);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/accounts/login")
    public BaseResponse<AdminDto.ResponseDto> adminLogin(@RequestBody AdminDto.RequestDto adminRequest) throws BaseException {
        return new BaseResponse<>(adminService.login(adminRequest));
    }

    @GetMapping("/reports")
    public BaseResponse<List<ReportResponseDto>> readReports(@RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        return new BaseResponse<>(adminService.readReports(page));
    }

    @PutMapping("/reports/execute")
    public BaseResponse<BaseResponseStatus> executeReport(@RequestParam Long id) throws BaseException {
        adminService.executeReport(id);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/certifications")
    public BaseResponse<List<CertificationResponseDto>> readCertifications() {
        return new BaseResponse<>(adminService.readCertifications());
    }

    @PutMapping("/certifications/execute")
    public BaseResponse<BaseResponseStatus> executeCertification(@RequestParam Long id) throws BaseException {
        adminService.executeCertification(id);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}
