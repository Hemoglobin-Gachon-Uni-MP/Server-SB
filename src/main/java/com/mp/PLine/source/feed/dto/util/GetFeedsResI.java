package com.mp.PLine.source.feed.dto.util;

public interface GetFeedsResI {
    // mapping DTO for feed
    Long getFeedId();
    Long getMemberId();
    String getProfileImg();
    String getNickname();
    String getContext();
    int getReplyCnt();
    int getCommentCnt();
    String getDate();
    int getAbo();
    int getRh();
    String getLocation();
    Boolean getIsReceiver();
}
