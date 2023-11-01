package com.mp.PLine.src.member.entity;

import com.mp.PLine.src.login.SocialType;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private String socialId;
//    private Long kakaoId;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private Status status;


    @Builder
    public Member(String name, String nickname, String birth, Long age, String phone, String gender, int abo,
                  int rh, String location, String profileImg, String email, String socialId, Role role, SocialType socialType, Status status) {
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
        this.email = email;
        this.socialId = socialId;
        this.role = role;
        this.socialType = socialType;
        this.status = status;
    }

    public static Member of(PostMemberReq postMemberReq, Long age, String socialId, Status status) {
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
                .socialId(socialId)
                .role(Role.MEMBER)
                .status(status)
                .build();
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}
