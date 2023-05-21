package com.mp.PLine.src.feed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentInfo {
    private int commentCnt;
    private List<CommentRes> commentRes;

    public CommentInfo(int commentCnt, List<CommentRes> commentRes) {
        this.commentCnt = commentCnt;
        this.commentRes = commentRes;
    }
}
