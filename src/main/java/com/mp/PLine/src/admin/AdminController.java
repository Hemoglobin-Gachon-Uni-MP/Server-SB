package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
        return new BaseResponse<>(adminService.readReports(page));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/reports/execute")
    public BaseResponse<BaseResponseStatus> executeReport(@RequestParam Long id) throws BaseException {
        adminService.executeReport(id);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }
}
