package com.mp.PLine.src.myPage.entity;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Certification extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String certificateNumber;
    private String certificateImg;
    private String date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Certification(Member member, String certificateNumber, String certificateImg, String date, Status status) {
        this.member = member;
        this.certificateNumber = certificateNumber;
        this.certificateImg = certificateImg;
        this.date = date;
        this.status = status;
    }

    public static Certification of(Member member, String certificateNumber, String certificateImg, String date, Status status) {
        return Certification.builder()
                .member(member)
                .certificateNumber(certificateNumber)
                .certificateImg(certificateImg)
                .date(date)
                .status(status)
                .build();
    }
}