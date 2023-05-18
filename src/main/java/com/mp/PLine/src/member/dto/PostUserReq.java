package com.mp.PLine.src.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUserReq {
    @ApiModelProperty(example = "정조은")
    private String name;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "2002.01.21")
    private String birth;
    @ApiModelProperty(example = "010-2449-2187")
    private String phone;
    @ApiModelProperty(example = "F (M, F)")
    private String gender;
    @ApiModelProperty(example = "O (A, B, O, AB)")
    private String abo;
    @ApiModelProperty(example = "Rh+ (Rh+, Rh-)")
    private String rh;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
}
