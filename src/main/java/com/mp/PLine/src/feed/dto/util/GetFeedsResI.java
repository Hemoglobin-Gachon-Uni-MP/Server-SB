package com.mp.PLine.src.feed.dto.util;

public interface GetFeedsResI {
    // mapping DTO for feed
    Long getFeedId();
    Long getMemberId();
    String getProfileImg();
    String getNickname();
    String getContext();
    int getCommentCnt();
    int getReplyCnt();
    String getDate();
    int getAbo();
    int getRh();
    String getLocation();
    Boolean getIsReceiver();
}
