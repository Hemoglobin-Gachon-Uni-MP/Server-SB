package com.mp.PLine.utils.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseUserIdReq {
    @ApiModelProperty(example = "7")
    private Long userId;
}
