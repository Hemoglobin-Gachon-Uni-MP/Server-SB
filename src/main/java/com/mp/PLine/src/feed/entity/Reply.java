package com.mp.PLine.src.feed.entity;

import com.mp.PLine.src.feed.dto.res.ReplyRes;
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
public class Reply extends BaseEntity {
    // Reply Entity for JPA
    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne @JoinColumn(name = "feed_id")
    private Feed feed;
    @ManyToOne @JoinColumn(name = "comment_id")
    private Comment comment;
    private String context;
    @Enumerated(EnumType.STRING)
    private Status status;

    public void delete() {
        this.status = Status.D;
    }

    public static Reply of(Member member, Feed feed, Comment comment, String context, Status status) {
        return Reply.builder()
                .member(member)
                .feed(feed)
                .comment(comment)
                .context(context)
                .status(status)
                .build();
    }

    public static ReplyRes toReplyRes(Reply reply) {
        return ReplyRes.from(reply);
    }

}