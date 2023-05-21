package com.mp.PLine.src.feed.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchFeedReq {
    @ApiModelProperty(example = "7")
    private Long userId;
    @ApiModelProperty(example = "피가 필요하묘")
    private String context;
}
