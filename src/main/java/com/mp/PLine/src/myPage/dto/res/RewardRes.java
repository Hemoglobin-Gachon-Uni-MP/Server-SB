package com.mp.PLine.src.myPage.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RewardRes {
    @ApiModelProperty(example = "헌혈 입문자")
    private String name;
    @ApiModelProperty(example = "https://p-line.s3.ap-northeast-2.amazonaws.com/profile/Group+20.png")
    private String medalImg;
    @ApiModelProperty(example = "23.10.28")
    private String date;

    public static RewardRes from(String name, String medalImg, String date) {
        return RewardRes.builder()
                .name(name)
                .medalImg(medalImg)
                .date(date)
                .build();
    }
}
