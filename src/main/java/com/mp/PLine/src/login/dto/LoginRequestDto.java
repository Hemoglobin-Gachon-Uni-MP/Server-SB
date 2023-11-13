package com.mp.PLine.src.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class LoginRequestDto {

    @Getter
    public static class LoginDto {
        @ApiModelProperty("엑세스 토큰에서 뽑아낸 SocialID 값")
        private Long socialId;
    }
}
