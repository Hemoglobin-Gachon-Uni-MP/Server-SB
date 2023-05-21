package com.mp.PLine.src.feed.dto.util;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public interface ReplyResI {
    Long getReplyId();
    Long getUserId();
    int getProfileImg();
    String getNickname();
    String getContext();
    Timestamp getDate();
}
