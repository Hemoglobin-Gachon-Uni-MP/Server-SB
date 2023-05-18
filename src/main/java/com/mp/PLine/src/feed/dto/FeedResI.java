package com.mp.PLine.src.feed.dto;

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
    @ApiModelProperty(example = "5/18")
    String getDate();
}
