package com.mp.PLine.src.myPage.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchUserReq {
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
}
