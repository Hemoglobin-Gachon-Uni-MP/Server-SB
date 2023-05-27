package com.mp.PLine.src.feed.entity;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class Reply extends BaseEntity {
    // Reply Entity for JPA
    @ManyToOne @JoinColumn(name = "user_id")
    private Member user;
    @ManyToOne @JoinColumn(name = "feed_id")
    private Feed feed;
    @ManyToOne @JoinColumn(name = "comment_id")
    private Comment comment;
    private String context;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Reply(Member user, Feed feed, Comment comment, String context, Status status) {
        this.user = user;
        this.feed = feed;
        this.comment = comment;
        this.context = context;
        this.status = status;
    }
}