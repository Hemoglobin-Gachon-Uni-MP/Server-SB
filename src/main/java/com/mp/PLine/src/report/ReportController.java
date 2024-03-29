package com.mp.PLine.src.report;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.src.report.dto.PostReportReq;
import com.mp.PLine.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "신고하기")
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final JwtService jwtService;

    /**
     * Report API
     * [POST] /reports
     */
    @ApiOperation("신고하기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2060, message = "신고하려는 유저가 존재하지 않는 유저입니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다."),
            @ApiResponse(code = 2046, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2047, message = "존재하지 않는 답글입니다."),
            @ApiResponse(code = 2061, message = "자기 자신은 신고할 수 없습니다."),
            @ApiResponse(code = 2062, message = "이미 신고가 완료되었습니다.")
    })
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> report(@RequestBody PostReportReq postReportReq) {
        // 이름 날짜 증서번호
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(reportService.report(memberId, postReportReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * Report API
     * [PATCH] /reports/status
     */
    @ApiOperation("신고 취소하기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2060, message = "신고하려는 유저가 존재하지 않는 유저입니다."),
            @ApiResponse(code = 2045, message = "존재하지 않는 게시물입니다."),
            @ApiResponse(code = 2046, message = "존재하지 않는 댓글입니다."),
            @ApiResponse(code = 2047, message = "존재하지 않는 답글입니다."),
            @ApiResponse(code = 2061, message = "자기 자신은 신고할 수 없습니다."),
            @ApiResponse(code = 2063, message = "신고가 접수되지 않았습니다.")
    })
    @ResponseBody
    @PatchMapping("/status")
    public BaseResponse<String> deleteReport(@RequestBody PostReportReq postReportReq) {
        // 이름 날짜 증서번호
        try {
            // get jwt from header
            Long memberId = jwtService.getMemberId();
            return new BaseResponse<>(reportService.deleteReport(memberId, postReportReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
