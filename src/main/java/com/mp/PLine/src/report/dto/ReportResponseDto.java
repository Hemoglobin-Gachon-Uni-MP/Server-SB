package com.mp.PLine.src.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponseDto {
    private Long id;
    private String fromMember;
    private String toMember;
    private String category;
    private Long feedOrCommentId;
    private String context;
    private String reason;
}
