package com.mp.PLine.src.feed.dto.res;

import com.mp.PLine.src.feed.FeedService;
import com.mp.PLine.src.feed.dto.util.CommentInfo;
import com.mp.PLine.src.feed.dto.util.CommentResI;
import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.src.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRes {
    // comment information of feed
    @ApiModelProperty("1")
    private Long commentId;
    @ApiModelProperty("7")
    private Long memberId;
    @ApiModelProperty(example = "1 (1, 2)")
    private String profileImg;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "댓글s")
    private String context;
    private List<ReplyRes> replyList;
    @ApiModelProperty(example = "05/09 오후 5:15")
    private String date;
    @ApiModelProperty(example = "true: 신고 O, false: 신고 X")
    private Boolean isReportedFromUser;

    public void setReplyList(List<ReplyRes> replyList) {
        this.replyList = replyList;
    }

    public static CommentRes of(CommentResI commentResInfo, List<ReplyRes> replyRes) {
        return CommentRes.builder()
                .commentId(commentResInfo.getCommentId())
                .memberId(commentResInfo.getMemberId())
                .profileImg(commentResInfo.getProfileImg())
                .nickname(commentResInfo.getNickname())
                .context(commentResInfo.getContext())
                .replyList(replyRes)
                .date(FeedService.longDate(commentResInfo.getDate()))
                .isReportedFromUser(commentResInfo.getIsReportedFromUser())
                .build();
    }

    public static CommentRes of (Comment comment, List<ReplyRes> replyRes) {
        Member member = comment.getMember();
        return CommentRes.builder()
                .commentId(comment.getId())
                .memberId(member.getId())
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .context(comment.getContext())
                .replyList(replyRes)
                .date(FeedService.longDate(comment.getCreatedAt()))
                .isReportedFromUser(null)
                .build();
    }
}
