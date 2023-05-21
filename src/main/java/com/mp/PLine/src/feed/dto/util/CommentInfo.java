package com.mp.PLine.src.feed.dto.util;

import com.mp.PLine.src.feed.dto.res.CommentRes;
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
