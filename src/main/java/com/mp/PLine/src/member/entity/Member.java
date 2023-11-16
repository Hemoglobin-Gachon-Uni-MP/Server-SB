package com.mp.PLine.src.member.entity;

import com.mp.PLine.src.login.oauth.SocialType;
import com.mp.PLine.src.member.dto.req.PostMemberReq;
import com.mp.PLine.src.member.dto.res.PostMemberRes;
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
@AllArgsConstructor(access= AccessLevel.PRIVATE)
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
    private Long socialId;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Member of(PostMemberReq postMemberReq, long age, String profileImg) {
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
                .profileImg(profileImg)
                .role(Role.MEMBER)
                .status(Status.A)
                .build();
    }

    public void signUpMember(PostMemberReq info, long age, String profileImg) {
        this.name = info.getName();
        this.nickname = info.getNickname();
        this.birth = info.getBirth();
        this.age = age;
        this.phone = info.getPhone();
        this.gender = info.getGender();
        this.abo = info.getAbo();
        this.rh = info.getRh();
        this.location = info.getLocation();
        this.profileImg = profileImg;
        this.role = Role.MEMBER;
        this.status = Status.A;
    }

    public static Member setEmptyMember(Long socialId) {
        return Member.builder()
                .socialType(SocialType.KAKAO)
                .socialId(socialId)
                .build();
    }

    public static PostMemberRes toPostMemberRes(String token, long memberId) {
        return PostMemberRes.builder()
                .jwt(token)
                .memberId(memberId)
                .build();
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public static long parseAge(String birth) {
        return 2023 - Long.parseLong(birth.substring(0, 4)) + 1;
    }

}
