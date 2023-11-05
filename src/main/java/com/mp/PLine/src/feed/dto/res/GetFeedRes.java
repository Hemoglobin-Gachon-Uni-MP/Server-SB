package com.mp.PLine.src.feed.dto.res;

import com.mp.PLine.src.feed.FeedService;
import com.mp.PLine.src.feed.dto.util.CommentInfo;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.src.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class GetFeedRes {
    // return feed info DTO
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
    private List<CommentRes> commentList;
    @ApiModelProperty(example = "05/09")
    private String date;
    @ApiModelProperty(example = "0: A, 1: B, 2: O, 3: AB")
    private int abo;
    @ApiModelProperty(example = "0: Rh+, 1: Rh-")
    private int rh;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "true: 수혈, false: 공혈")
    private Boolean isReceiver;
    @ApiModelProperty(example = "true: 수혈, false: 공혈")
    private Boolean isReported;

    public GetFeedRes(Long feedId, Long memberId, String profileImg, String nickname, String context,
                      int commentCnt, List<CommentRes> commentList,
                      String date, int abo, int rh, String location, Boolean isReceiver, Boolean isReported) {
        this.feedId = feedId;
        this.memberId = memberId;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.context = context;
        this.commentCnt = commentCnt;
        this.commentList = commentList;
        this.date = date;
        this.abo = abo;
        this.rh = rh;
        this.location = location;
        this.isReceiver = isReceiver;
        this.isReported = isReported;
    }

    public static GetFeedRes of(Feed feed, Member member, CommentInfo commentInfo, Boolean isReported) {
        return GetFeedRes.builder()
                .feedId(feed.getId())
                .memberId(member.getId())
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .context(feed.getContext())
                .commentCnt(commentInfo.getCommentCnt())
                .commentList(commentInfo.getCommentRes())
                .date(FeedService.shortDate(feed.getCreatedAt()))
                .abo(feed.getAbo())
                .rh(feed.getRh())
                .location(feed.getLocation())
                .isReceiver(feed.getIsReceiver())
                .isReported(isReported)
                .build();
    }
}
