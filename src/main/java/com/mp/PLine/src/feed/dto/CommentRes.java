package com.mp.PLine.src.feed.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentRes {
    @ApiModelProperty("1")
    private Long commentId;
    @ApiModelProperty("7")
    private Long userId;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "댓글s")
    private String context;
    private List<ReplyRes> replyList;
    @ApiModelProperty(example = "05/09 오후 5:15")
    private String date;

    public CommentRes(Long commentId, Long userId, int profileImg, String nickname,
                      String context, List<ReplyRes> reply, String date) {
        this.commentId = commentId;
        this.userId = userId;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.context = context;
        this.replyList = reply;
        this.date = date;
    }
}
