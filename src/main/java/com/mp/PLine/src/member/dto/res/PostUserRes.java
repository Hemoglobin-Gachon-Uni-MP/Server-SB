package com.mp.PLine.src.member.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUserRes {
    @ApiModelProperty(example = "jwt....")
    private String jwt;
    @ApiModelProperty(example = "1")
    private Long userId;

    public PostUserRes(String jwt, Long userId) {
        this.jwt = jwt;
        this.userId = userId;
    }
}
