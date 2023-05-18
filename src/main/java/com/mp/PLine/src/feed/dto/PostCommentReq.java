package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCommentReq {
    @ApiModelProperty(example = "7")
    private Long userId;
    @ApiModelProperty(example = "댓글댓글댓글댓글댓글")
    private String context;
}
