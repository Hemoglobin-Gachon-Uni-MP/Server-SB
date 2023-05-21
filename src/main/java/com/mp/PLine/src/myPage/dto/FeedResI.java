package com.mp.PLine.src.myPage.dto;

import io.swagger.annotations.ApiModelProperty;

public interface FeedResI {
    @ApiModelProperty(example = "1")
    Long getFeedId();
    @ApiModelProperty(example = "1")
    Long getUserId();
    @ApiModelProperty(example = "보리")
    String getNickname();
    @ApiModelProperty(example = "1 (1, 2)")
    int getProfileImg();
    @ApiModelProperty(example = "안녕 반갑수다")
    String getContext();
    @ApiModelProperty(example = "3")
    int getCommentCnt();
    int getReplyCnt();
    @ApiModelProperty(example = "05/18")
    String getDate();
    @ApiModelProperty(example = "T: 수혈, F: 공혈")
    String getIsReceiver();


}
