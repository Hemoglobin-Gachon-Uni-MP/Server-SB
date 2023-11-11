package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "관리자 앱")
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/reports")
    public BaseResponse<List<ReportResponseDto.ReportReadResponseDto>> readReports(@RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        return new  BaseResponse<>(adminService.readReports(page));
    }

}
