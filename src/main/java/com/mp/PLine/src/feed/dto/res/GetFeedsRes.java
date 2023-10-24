package com.mp.PLine.src.feed.dto.res;

import com.mp.PLine.src.feed.dto.util.GetFeedsResI;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
public class GetFeedsRes {
    // return feed list DTO
    @ApiModelProperty(example = "1")
    private Long feedId;
    @ApiModelProperty(example = "7")
    private Long memberId;
    @ApiModelProperty(example = "1 (1, 2)")
    private String profileImg;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "안녕 반갑수다")
    private String context;
    @ApiModelProperty(example = "3")
    private int commentCnt;
    @ApiModelProperty(example = "05/09")
    private String date;
    @ApiModelProperty(example = "0: A, 1: B, 2: O, 3: AB")
    private int abo;
    @ApiModelProperty(example = "0: Rh+, 1: Rh-")
    private int rh;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "T: 수혈, F: 공혈")
    private Boolean isReceiver;

    public static GetFeedsRes from(GetFeedsResI feedMappingInfo) {
        return GetFeedsRes.builder()
                .feedId(feedMappingInfo.getFeedId())
                .memberId(feedMappingInfo.getMemberId())
                .profileImg(feedMappingInfo.getProfileImg())
                .nickname(feedMappingInfo.getNickname())
                .commentCnt(feedMappingInfo.getCommentCnt())
                .date(feedMappingInfo.getDate())
                .abo(feedMappingInfo.getAbo())
                .rh(feedMappingInfo.getRh())
                .location(feedMappingInfo.getLocation())
                .isReceiver(feedMappingInfo.getIsReceiver())
                .build();
    }
}
