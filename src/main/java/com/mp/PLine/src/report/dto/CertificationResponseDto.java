package com.mp.PLine.src.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CertificationResponseDto {
    private String member;
    private String certificateNumber;
    private String certificateImg;
    private String date;
}
