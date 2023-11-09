package com.mp.PLine.src.myPage.dto.res;

import com.mp.PLine.src.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class GetRewardRes {
    // return certification reward list DTO
    @ApiModelProperty(example = "1")
    private Long memberId;
    @ApiModelProperty(example = "10")
    private Long certificationCnt;
    private List<RewardRes> rewardList;

    public GetRewardRes(Long memberId, Long certificationCnt, List<RewardRes> rewardList) {
        this.memberId = memberId;
        this.certificationCnt = certificationCnt;
        this.rewardList = rewardList;
    }

    public static GetRewardRes of(Member member, Long certificationCnt, List<RewardRes> rewardList) {
        return GetRewardRes.builder()
                .memberId(member.getId())
                .certificationCnt(certificationCnt)
                .rewardList(rewardList)
                .build();
    }
}
