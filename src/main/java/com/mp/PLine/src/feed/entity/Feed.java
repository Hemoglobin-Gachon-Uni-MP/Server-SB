package com.mp.PLine.src.feed.entity;

import com.mp.PLine.src.feed.FeedService;
import com.mp.PLine.src.feed.dto.req.PostFeedReq;
import com.mp.PLine.src.feed.dto.res.GetFeedRes;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseEntity {
    // Feed Entity for JPA
    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;
    private String context;
    private int abo;
    private int rh;
    private String location;
    private Boolean isReceiver;
    @Enumerated(EnumType.STRING)
    private Status status;

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

    public GetFeedRes toGetFeedResponse() {
        Member member = this.getMember();
        return GetFeedRes.builder()
                .feedId(this.getId())
                .memberId(member.getId())
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .context(this.getContext())
                .commentCnt(0)
                .date(FeedService.shortDate(this.getCreatedAt()))
                .abo(this.getAbo())
                .rh(this.getRh())
                .location(this.getLocation())
                .isReceiver(this.getIsReceiver())
                .build();
    }

    public void delete() {
        this.status = Status.D;
    }
}