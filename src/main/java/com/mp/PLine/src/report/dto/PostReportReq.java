package com.mp.PLine.src.report.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReportReq {
    @ApiModelProperty(example = "2")
    private Long memberId;
    @ApiModelProperty(example = "3")
    private Long reportedMemberId;
    @ApiModelProperty(example = "F: Feed, C: Comment, R: Reply / 글자 하나만 보내시면 됩니다")
    private String category;
    @ApiModelProperty(example = "2 / 피드면 피드 아이디, 댓글이면 댓글 아이디, 답글이면 답글 아이디")
    private Long feedOrCommentId;
    @ApiModelProperty(example = "맘에 안드네요 ㅋㅋ")
    private String reason;
}
