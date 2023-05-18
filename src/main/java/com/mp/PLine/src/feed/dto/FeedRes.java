package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;

public class FeedRes {
    @ApiModelProperty(example = "1")
    private Long feedId;
    @ApiModelProperty(example = "1")
    private Long userId;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
    @ApiModelProperty(example = "안녕 반갑수다")
    private String context;
    @ApiModelProperty(example = "3")
    private int commentCnt;
    @ApiModelProperty(example = "5/18")
    private String date;
}
