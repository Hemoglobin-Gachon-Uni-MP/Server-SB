package com.mp.PLine.src.report.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class ReportRequestDto {

    @Getter
    public static class FeedReportDto {
        @ApiModelProperty(example = "신고글 ID")
        private Long id;
        @ApiModelProperty(example = "게시물 ID")
        private Long feedId;
        @ApiModelProperty(example = "F")
        private String category;
    }

    @Getter
    public static class CommentReportDto {
        @ApiModelProperty(example = "신고글 ID")
        private Long id;
        @ApiModelProperty(example = "(대)댓글 ID")
        private Long commentId;
        @ApiModelProperty(example = "C or R")
        private String category;
    }
}
