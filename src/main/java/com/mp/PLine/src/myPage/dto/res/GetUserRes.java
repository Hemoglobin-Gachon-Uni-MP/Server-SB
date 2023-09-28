package com.mp.PLine.src.myPage.dto.res;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.MyPageService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class GetUserRes {
    // return user info DTO
    @ApiModelProperty(example = "1")
    private Long userId;
    @ApiModelProperty(example = "정조은")
    private String name;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "2002.01.21")
    private String birth;
    @ApiModelProperty(example = "010-2449-2187")
    private String phone;
    @ApiModelProperty(example = "M: 남자, F: 여자")
    private String gender;
    @ApiModelProperty(example = "Rh+O (Rh+A, Rh+B, Rh+O, Rh+AB, Rh-A, Rh-B, Rh-O, Rh-AB)")
    private String blood;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
    private List<FeedRes> feedList;

    public GetUserRes(Long userId, String name, String nickname, String birth, String phone,
                      String gender, String blood, String location, int profileImg, List<FeedRes> feedList) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.phone = phone;
        this.gender = gender;
        this.blood = blood;
        this.location = location;
        this.profileImg = profileImg;
        this.feedList = feedList;
    }

    public static GetUserRes of(Member member, String genderStrKorean, List<FeedRes> feedList) {
        return GetUserRes.builder()
                .userId(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .birth(member.getBirth())
                .phone(member.getPhone())
                .gender(genderStrKorean)
                .blood(MyPageService.blood(member.getRh(), member.getAbo()))
                .location(member.getLocation())
                .profileImg(member.getProfileImg())
                .feedList(feedList)
                .build();
    }

}
