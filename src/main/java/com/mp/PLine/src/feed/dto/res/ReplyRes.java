package com.mp.PLine.src.feed.dto.res;

import com.mp.PLine.src.feed.FeedService;
import com.mp.PLine.src.feed.dto.util.ReplyResI;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReplyRes {
    // reply information of comment DTO
    @ApiModelProperty("1")
    private Long replyId;
    @ApiModelProperty("7")
    private Long memberId;
    @ApiModelProperty(example = "1 (1, 2)")
    private String profileImg;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "답글s")
    private String context;
    @ApiModelProperty(example = "05/09 오후 5:15")
    private String date;
    @ApiModelProperty(example = "true: 신고 O, false: 신고 X")
    private Boolean isReportedFromUser;

    public static ReplyRes from(ReplyResI replyResInfo) {
        return ReplyRes.builder()
                .replyId(replyResInfo.getReplyId())
                .memberId(replyResInfo.getMemberId())
                .profileImg(replyResInfo.getProfileImg())
                .nickname(replyResInfo.getNickname())
                .context(replyResInfo.getContext())
                .date(FeedService.longDate(replyResInfo.getDate()))
                .isReportedFromUser(replyResInfo.getIsReportedFromUser())
                .build();
    }
}
