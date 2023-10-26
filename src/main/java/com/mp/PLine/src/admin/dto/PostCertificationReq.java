package com.mp.PLine.src.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PostCertificationReq {
    @ApiModelProperty(example = "정조은")
    private String name;
    @ApiModelProperty(example = "01-21-000000")
    private String certificationNum;
    @ApiModelProperty(example = "2023.10.27")
    private String date;
    @ApiModelProperty(example = "11.png")
    private MultipartFile image;
}
