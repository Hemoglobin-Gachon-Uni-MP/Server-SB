package com.mp.PLine.src.feed.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReplyReq {
    // create reply DTO
    @ApiModelProperty(example = "7")
    private Long userId;
    @ApiModelProperty(example = "3")
    private Long feedId;
    @ApiModelProperty(example = "댓글댓글댓글댓글댓글")
    private String context;
}
