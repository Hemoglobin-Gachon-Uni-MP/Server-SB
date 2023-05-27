package com.mp.PLine.src.feed.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder
public class ReplyRes {
    // reply information of comment DTO
    @ApiModelProperty("1")
    private Long replyId;
    @ApiModelProperty("7")
    private Long userId;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "답글s")
    private String context;
    @ApiModelProperty(example = "05/09 오후 5:15")
    private String date;
}
