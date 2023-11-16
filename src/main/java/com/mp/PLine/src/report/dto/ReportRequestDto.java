package com.mp.PLine.src.report.dto;

import lombok.Getter;

public class ReportRequestDto {
    @Getter
    public static class commentReportDto {
        private Long id;
        private Long commentId;
        private String category;
    }
}
