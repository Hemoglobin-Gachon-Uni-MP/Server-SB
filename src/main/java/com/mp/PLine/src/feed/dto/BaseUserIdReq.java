package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseUserIdReq {
    @ApiModelProperty(example = "7")
    private Long userId;
}
