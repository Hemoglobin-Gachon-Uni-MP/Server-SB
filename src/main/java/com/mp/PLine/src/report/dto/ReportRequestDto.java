package com.mp.PLine.src.report.dto;

import lombok.Getter;

public class ReportRequestDto {

    @Getter
    public static class FeedReportDto {
        private Long id;
        private Long feedId;
        private String category;
    }

    @Getter
    public static class CommentReportDto {
        private Long id;
        private Long commentId;
        private String category;
    }
}
