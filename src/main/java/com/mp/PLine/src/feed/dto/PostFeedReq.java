package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostFeedReq {
    @ApiModelProperty(example = "7")
    private Long userId;
    @ApiModelProperty(example = "피가 필요하묘")
    private String context;
    @ApiModelProperty(example = "O (A, B, O, AB)")
    private String abo;
    @ApiModelProperty(example = "Rh+ (Rh+, Rh-)")
    private String rh;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "수혈자 : T, 공혈자 : F")
    private String isReceiver;
}
