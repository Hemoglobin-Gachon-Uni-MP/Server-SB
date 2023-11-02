package com.mp.PLine.source.member.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostMemberRes {
    // edit user information dto
    @ApiModelProperty(example = "jwt....")
    private String jwt;
    @ApiModelProperty(example = "1")
    private Long memberId;

    public PostMemberRes(String jwt, Long memberId) {
        this.jwt = jwt;
        this.memberId = memberId;
    }
}
