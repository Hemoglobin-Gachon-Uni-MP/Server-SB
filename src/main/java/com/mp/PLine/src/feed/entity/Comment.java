package com.mp.PLine.src.feed.entity;

import com.mp.PLine.src.feed.FeedService;
import com.mp.PLine.src.feed.dto.res.CommentRes;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@BatchSize(size = 30)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    // Comment Entity for JPA
    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne @JoinColumn(name = "feed_id")
    private Feed feed;
    private String context;
    @Enumerated(EnumType.STRING)
    private Status status;

    public void delete() {
        this.status = Status.D;
    }

    public static Comment of (Member member, Feed feed, String context, Status status) {
        return Comment.builder()
                .member(member)
                .feed(feed)
                .context(context)
                .status(status)
                .build();
    }

    public static CommentRes toCommentRes(Comment comment) {
        Member member = comment.getMember();
        return CommentRes.builder()
                .commentId(comment.getId())
                .memberId(member.getId())
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .context(comment.getContext())
                .date(FeedService.longDate(comment.getCreatedAt()))
                .build();
    }
}