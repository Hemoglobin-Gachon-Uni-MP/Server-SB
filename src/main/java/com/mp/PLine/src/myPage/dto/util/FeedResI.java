package com.mp.PLine.src.myPage.dto.util;

import io.swagger.annotations.ApiModelProperty;

public interface FeedResI {
    // mapping DTO for user's feed
    @ApiModelProperty(example = "1")
    Long getFeedId();
    @ApiModelProperty(example = "1")
    Long getMemberId();
    @ApiModelProperty(example = "보리")
    String getNickname();
    @ApiModelProperty(example = "1 (1, 2)")
    String getProfileImg();
    @ApiModelProperty(example = "안녕 반갑수다")
    String getContext();
    @ApiModelProperty(example = "3")
    int getReplyCnt();
    @ApiModelProperty(example = "3")
    int getCommentCnt();
    @ApiModelProperty(example = "05/18")
    String getDate();
    @ApiModelProperty(example = "T: 수혈, F: 공혈")
    Boolean getIsReceiver();


}
