package com.mp.PLine.src.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class AdminDto {
    @Getter
    public static class RequestDto {
        @ApiModelProperty(example = "a로 시작하는 키값")
        private String key;
    }

    @Getter
    @AllArgsConstructor
    public static class ResponseDto {
        private String jwt;
    }
}
