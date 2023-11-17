package com.mp.PLine.src.report.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CertificationResponseDto {
    @ApiModelProperty(example = "헌혈증 ID")
    private Long id;
    @ApiModelProperty(example = "닉네임")
    private String member;
    @ApiModelProperty(example = "헌혈증 번호")
    private String certificateNumber;
    private String certificateImg;
    private String date;
}
