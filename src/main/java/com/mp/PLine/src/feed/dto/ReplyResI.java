package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public interface ReplyResI {
    @ApiModelProperty("1")
    Long getReplyId();
    @ApiModelProperty("7")
    Long getUserId();
    @ApiModelProperty(example = "1 (1, 2)")
    int getProfileImg();
    @ApiModelProperty(example = "보리")
    String getNickname();
    @ApiModelProperty(example = "답글s")
    String getContext();
    @ApiModelProperty(example = "05/09 오후 5:15")
    Timestamp getDate();
}
