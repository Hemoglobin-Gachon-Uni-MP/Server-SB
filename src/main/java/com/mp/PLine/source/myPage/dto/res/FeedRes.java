package com.mp.PLine.source.myPage.dto.res;

import com.mp.PLine.source.myPage.dto.util.FeedResI;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FeedRes {
    // mapping DTO for feed
    @ApiModelProperty(example = "1")
    private Long feedId;
    @ApiModelProperty(example = "1")
    private Long memberId;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "1 (1, 2)")
    private String profileImg;
    @ApiModelProperty(example = "안녕 반갑수다")
    private String context;
    @ApiModelProperty(example = "3")
    private int commentCnt;
    @ApiModelProperty(example = "05/18")
    private String date;
    @ApiModelProperty(example = "T: 수혈, F: 공혈")
    private Boolean isReceiver;

    public static FeedRes from(FeedResI feedResInfo) {
        return FeedRes.builder()
                .feedId(feedResInfo.getFeedId())
                .memberId(feedResInfo.getMemberId())
                .nickname(feedResInfo.getNickname())
                .profileImg(feedResInfo.getProfileImg())
                .context(feedResInfo.getContext())
                .commentCnt(feedResInfo.getCommentCnt())
                .date(feedResInfo.getDate())
                .isReceiver(feedResInfo.getIsReceiver())
                .build();
    }

}
