package com.mp.PLine.utils;

import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.req.PatchFeedReq;
import com.mp.PLine.src.feed.dto.req.PostCommentReq;
import com.mp.PLine.src.feed.dto.req.PostFeedReq;
import com.mp.PLine.src.feed.dto.req.PostReplyReq;
import com.mp.PLine.src.member.dto.req.PostUserReq;
import com.mp.PLine.src.myPage.dto.req.PatchUserReq;

public class Validation {
    /** 회원 가입 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkSignUp(PostUserReq postUserReq) {
        // 빈 칸 확인
        if(postUserReq.getName().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NAME;
        if(postUserReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(postUserReq.getBirth().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_BIRTH;
        if(postUserReq.getPhone().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_PHONE;
        if(postUserReq.getGender().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_GENDER;
        if(postUserReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // 형식
        String nickname = postUserReq.getNickname();
        String phone = postUserReq.getPhone();
        String birth = postUserReq.getBirth();
        String gender = postUserReq.getGender();
        int abo = postUserReq.getAbo(), rh = postUserReq.getRh();
        int profileImg = postUserReq.getProfileImg();

        if(nickname.length() < 1 || nickname.length() > 8) return BaseResponseStatus.POST_USERS_INVAlID_NICKNAME;
        if(!ValidationRegex.isRegexPhone(phone)) return BaseResponseStatus.POST_USERS_INVAlID_PHONE;
        if(!ValidationRegex.isRegexBirth(birth)) return BaseResponseStatus.POST_USERS_INVALID_BIRTH;
        if(!(gender.equals("M") || gender.equals("F"))) return BaseResponseStatus.POST_USERS_INVALID_GENDER;
        if(abo < 0 || abo > 3) return BaseResponseStatus.POST_USERS_INVALID_ABO;
        if(rh < 0 || rh > 1) return BaseResponseStatus.POST_USERS_INVALID_RH;
        if(!(profileImg == 1 || profileImg == 2)) return BaseResponseStatus.POST_USERS_INVALID_PROFILE;

        return BaseResponseStatus.SUCCESS;
    }

    /** 내 정보 수정 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkUpdateUser(PatchUserReq patchUserReq) {
        // 빈 칸 확인
        if(patchUserReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(patchUserReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // 형식
        String nickname = patchUserReq.getNickname();

        if(nickname.length() < 1 || nickname.length() > 8) return BaseResponseStatus.POST_USERS_INVAlID_NICKNAME;

        return BaseResponseStatus.SUCCESS;
    }

    /** 게시물 업로드 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkPostFeed(PostFeedReq postFeedReq) {
        if(postFeedReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(postFeedReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;
        if(postFeedReq.getLocation().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_LOCATION;
        if(postFeedReq.getIsReceiver().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_IS_RECEIVER;

        int abo = postFeedReq.getAbo(), rh = postFeedReq.getRh();
        String isReceiver = postFeedReq.getIsReceiver();
        if(abo < 0 || abo > 3) return BaseResponseStatus.POST_FEEDS_INVALID_ABO;
        if(rh < 0 || rh > 1) return BaseResponseStatus.POST_FEEDS_INVALID_RH;
        if(!(isReceiver.equals("T") || isReceiver.equals("F"))) return BaseResponseStatus.POST_FEEDS_INVALID_IS_RECEIVER;

        return BaseResponseStatus.SUCCESS;
    }

    /** 게시물 수정 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkUpdateFeed(PatchFeedReq patchFeedReq) {
        // 빈 칸 확인
        if(patchFeedReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(patchFeedReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }

    /** 댓글 업로드 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkPostComment(PostCommentReq postCommentReq) {
        // 빈 칸 확인
        if(postCommentReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(postCommentReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }

    /** 답글 업로드 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkPostReply(PostReplyReq postReplyReq) {
        // 빈 칸 확인
        if(postReplyReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(postReplyReq.getFeedId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_FEED;
        if(postReplyReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }
}
