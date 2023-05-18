package com.mp.PLine.src.member.entity;

import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseEntity {
    private String name;

    private String nickname;
    private String birth;
    private Long age;
    private String phone;
    private String gender;
    private int abo;
    private int rh;
    private String location;
    private int profileImg;
    private Long kakaoId;
    @Enumerated(EnumType.STRING)
    private Status status;


    @Builder
    public Member(String name, String nickname, String birth, Long age, String phone, String gender, int abo,
                  int rh, String location, int profileImg, Long kakaoId, Status status) {
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
}
