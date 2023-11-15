package com.mp.PLine.src.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @ApiModelProperty("엑세스 토큰에서 뽑아낸 IdToken 값")
    private String idToken;
}
