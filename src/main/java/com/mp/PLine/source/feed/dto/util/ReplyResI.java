package com.mp.PLine.source.feed.dto.util;

import java.sql.Timestamp;

public interface ReplyResI {
    // mapping DTO for reply
    Long getReplyId();
    Long getMemberId();
    String getProfileImg();
    String getNickname();
    String getContext();
    Timestamp getDate();
}
