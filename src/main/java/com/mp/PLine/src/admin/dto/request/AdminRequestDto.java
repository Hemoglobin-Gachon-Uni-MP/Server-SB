package com.mp.PLine.src.admin.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;


public class AdminRequestDto {
    @Getter
    public static class LoginDto {
        @ApiModelProperty(example = "a로 시작하는 키값")
        private String key;
    }

    @Getter
    public static class DetailReportDto {
        private Long reportId;
        private String category;
        private Long FeedOrCommentId;
    }
}
