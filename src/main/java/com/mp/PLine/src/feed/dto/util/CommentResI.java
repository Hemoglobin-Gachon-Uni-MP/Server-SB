package com.mp.PLine.src.feed.dto.util;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Time;
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
