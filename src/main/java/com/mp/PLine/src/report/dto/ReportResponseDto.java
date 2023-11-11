package com.mp.PLine.src.report.dto;

import lombok.Builder;
import lombok.Getter;

public class ReportResponseDto {
    @Getter
    @Builder
    public static class ReportReadResponseDto {
        private String fromMember;
        private String toMember;
        private String category;
        private Long feedOrCommentId;
        private String reason;
    }
}
