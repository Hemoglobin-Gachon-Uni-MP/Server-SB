package com.mp.PLine.src.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class AdminDto {
    @Getter
    public static class RequestDto {
        private String key;
    }

    @Getter
    @AllArgsConstructor
    public static class ResponseDto {
        private String jwt;
    }
}
