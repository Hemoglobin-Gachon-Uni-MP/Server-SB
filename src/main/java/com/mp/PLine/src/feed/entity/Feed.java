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
public class Feed extends BaseEntity {
    @ManyToOne @JoinColumn(name = "user_id")
    private Member user;
    private String context;
    private String abo;
    private String rh;
    private String location;
    private String isReceiver;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Feed(Member user, String context, String abo, String rh, String location, String isReceiver, Status status) {
        this.user = user;
        this.context = context;
        this.abo = abo;
        this.rh = rh;
        this.location = location;
        this.isReceiver = isReceiver;
        this.status = status;
    }
}