package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;

public interface GetFeedsResI {
    @ApiModelProperty(example = "1")
    Long getFeedId();
    @ApiModelProperty(example = "7")
    Long getUserId();
    @ApiModelProperty(example = "1 (1, 2)")
    int getProfileImg();
    @ApiModelProperty(example = "보리")
    String getNickname();
    @ApiModelProperty(example = "안녕 반갑수다")
    String getContext();
    @ApiModelProperty(example = "3")
    int getCommentCnt();
    int getReplyCnt();
    @ApiModelProperty(example = "05/09")
    String getDate();
    @ApiModelProperty(example = "0: A, 1: B, 2: O, 3: AB")
    int getAbo();
    @ApiModelProperty(example = "0: Rh+, 1: Rh-")
    int getRh();
    @ApiModelProperty(example = "서울시 관악구")
    String getLocation();
    @ApiModelProperty(example = "T: 수혈, F: 공혈")
    String getIsReceiver();
}
