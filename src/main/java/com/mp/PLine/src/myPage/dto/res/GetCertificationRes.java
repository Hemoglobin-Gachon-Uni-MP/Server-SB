package com.mp.PLine.src.myPage.dto.res;

import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.MyPageService;
import com.mp.PLine.src.myPage.entity.Certification;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class GetCertificationRes {
    // return certification info DTO
    @ApiModelProperty(example = "1")
    private Long certificationId;
    @ApiModelProperty(example = "1")
    private Long memberId;
    @ApiModelProperty(example = "정조은")
    private String name;
    @ApiModelProperty(example = "보리")
    private String certificationNum;
    @ApiModelProperty(example = "2002.01.21")
    private String date;

    public GetCertificationRes(Long certificationId, Long memberId, String name, String certificationNum, String date) {
        this.certificationId = certificationId;
        this.memberId = memberId;
        this.name = name;
        this.certificationNum = certificationNum;
        this.date = date;
    }

    public static GetCertificationRes of(Member member, Certification certification) {
        return GetCertificationRes.builder()
                .certificationId(certification.getId())
                .memberId(member.getId())
                .name(member.getName())
                .certificationNum(certification.getCertificateNumber())
                .date(certification.getDate())
                .build();
    }

}
