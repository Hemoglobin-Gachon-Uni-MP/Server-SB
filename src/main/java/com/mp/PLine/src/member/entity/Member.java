package com.mp.PLine.src.member.entity;

import com.mp.PLine.src.member.dto.req.PostMemberReq;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseEntity {
    // Member Entity for JPA
    private String name;

    private String nickname;
    private String birth;
    private Long age;
    private String phone;
    private String gender;
    private int abo;
    private int rh;
    private String location;
    private String profileImg;
    private Long kakaoId;
    @Enumerated(EnumType.STRING)
    private Status status;


    @Builder
    public Member(String name, String nickname, String birth, Long age, String phone, String gender, int abo,
                  int rh, String location, String profileImg, Long kakaoId, Status status) {
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.abo = abo;
        this.rh = rh;
        this.location = location;
        this.profileImg = profileImg;
        this.kakaoId = kakaoId;
        this.status = status;
    }

    public static Member of(PostMemberReq postMemberReq, Long age, Long kakaoId, Status status) {
        return Member.builder()
                .name(postMemberReq.getName())
                .nickname(postMemberReq.getNickname())
                .birth(postMemberReq.getBirth())
                .age(age)
                .phone(postMemberReq.getPhone())
                .gender(postMemberReq.getGender())
                .abo(postMemberReq.getAbo())
                .rh(postMemberReq.getRh())
                .location(postMemberReq.getLocation())
                .profileImg(postMemberReq.getProfileImg())
                .kakaoId(kakaoId)
                .status(status)
                .build();
    }

}
