package com.mp.PLine.src.feed.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetFeedRes {
    @ApiModelProperty(example = "1")
    private Long feedId;
    @ApiModelProperty(example = "7")
    private Long userId;
    @ApiModelProperty(example = "1 (1, 2)")
    private int profileImg;
    @ApiModelProperty(example = "보리")
    private String nickname;
    @ApiModelProperty(example = "안녕 반갑수다")
    private String context;
    @ApiModelProperty(example = "3")
    private int commentCnt;
    private List<CommentRes> commentList;
    @ApiModelProperty(example = "05/09")
    private String date;
    @ApiModelProperty(example = "0: A, 1: B, 2: O, 3: AB")
    private int abo;
    @ApiModelProperty(example = "0: Rh+, 1: Rh-")
    private int rh;
    @ApiModelProperty(example = "서울시 관악구")
    private String location;
    @ApiModelProperty(example = "T: 수혈, F: 공혈")
    private String isReceiver;

    public GetFeedRes(Long feedId, Long userId, int profileImg, String nickname, String context,
                      int commentCnt, List<CommentRes> commentList,
                      String date, int abo, int rh, String location, String isReceiver) {
        this.feedId = feedId;
        this.userId = userId;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.context = context;
        this.commentCnt = commentCnt;
        this.commentList = commentList;
        this.date = date;
        this.abo = abo;
        this.rh = rh;
        this.location = location;
        this.isReceiver = isReceiver;
    }
}
