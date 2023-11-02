package com.mp.PLine.source.myPage.dto.res;

import com.mp.PLine.source.member.entity.Member;
import com.mp.PLine.source.myPage.MyPageService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class GetMemberRes {
    // return user info DTO
    @ApiModelProperty(example = "1")
    private Long memberId;
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
    private String profileImg;
    private List<FeedRes> feedList;

    public GetMemberRes(Long memberId, String name, String nickname, String birth, String phone,
                        String gender, String blood, String location, String profileImg, List<FeedRes> feedList) {
        this.memberId = memberId;
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

    public static GetMemberRes of(Member member, String genderStrKorean, List<FeedRes> feedList) {
        return GetMemberRes.builder()
                .memberId(member.getId())
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
