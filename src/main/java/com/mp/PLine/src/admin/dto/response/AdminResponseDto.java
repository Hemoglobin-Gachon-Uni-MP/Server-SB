package com.mp.PLine.src.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AdminResponseDto {

    @Getter
    @AllArgsConstructor
    public static class ResponseDto {
        private String jwt;
    }
}
