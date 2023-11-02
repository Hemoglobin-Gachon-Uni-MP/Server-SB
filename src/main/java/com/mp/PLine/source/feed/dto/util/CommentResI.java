package com.mp.PLine.source.feed.dto.util;

import java.sql.Timestamp;

public interface CommentResI {
    // mapping DTO for comment
    Long getCommentId();
    Long getMemberId();
    String getProfileImg();
    String getNickname();
    String getContext();
    Timestamp getDate();
}
