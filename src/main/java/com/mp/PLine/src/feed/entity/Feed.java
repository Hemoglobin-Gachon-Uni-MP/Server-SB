package com.mp.PLine.src.feed.entity;

import com.mp.PLine.src.feed.dto.req.PostFeedReq;
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
public class Feed extends BaseEntity {
    // Feed Entity for JPA
    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;
    private String context;
    private int abo;
    private int rh;
    private String location;
    private String isReceiver;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Feed(Member member, String context, int abo, int rh, String location, String isReceiver, Status status) {
        this.member = member;
        this.context = context;
        this.abo = abo;
        this.rh = rh;
        this.location = location;
        this.isReceiver = isReceiver;
        this.status = status;
    }

    public static Feed of(Member member, PostFeedReq postFeedReq, Status status) {
        return Feed.builder()
                .member(member)
                .context(postFeedReq.getContext())
                .abo(postFeedReq.getAbo())
                .rh(postFeedReq.getRh())
                .location(postFeedReq.getLocation())
                .isReceiver(postFeedReq.getIsReceiver())
                .status(status)
                .build();
    }

}