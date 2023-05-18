package com.mp.PLine.src.myPage.dto;

import com.mp.PLine.src.feed.dto.FeedRes;
import com.mp.PLine.src.feed.dto.FeedResI;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetUserRes {
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
    @ApiModelProperty(example = "F (M, F)")
    private String gender;
    @ApiModelProperty(example = "O (Rh+A, Rh+B, Rh+O, Rh+AB, Rh-A, Rh-B, Rh-O, Rh-AB)")
    private String blood;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
    private List<FeedResI> feedList;

    public GetUserRes(Long userId, String name, String nickname, String birth, String phone,
                      String gender, String blood, String location, int profileImg, List<FeedResI> feedList) {
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
}
