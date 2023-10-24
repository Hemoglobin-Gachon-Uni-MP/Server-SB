package com.mp.PLine.src.feed.entity;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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

    @Builder
    public Reply(Member member, Feed feed, Comment comment, String context, Status status) {
        this.member = member;
        this.feed = feed;
        this.comment = comment;
        this.context = context;
        this.status = status;
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

}