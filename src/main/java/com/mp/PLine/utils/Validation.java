package com.mp.PLine.utils;

import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.req.PatchFeedReq;
import com.mp.PLine.src.feed.dto.req.PostCommentReq;
import com.mp.PLine.src.feed.dto.req.PostFeedReq;
import com.mp.PLine.src.feed.dto.req.PostReplyReq;
import com.mp.PLine.src.member.dto.req.PostUserReq;
import com.mp.PLine.src.myPage.dto.req.PatchUserReq;

public class Validation {
    /** check blank & form when sign-up **/
    public static BaseResponseStatus checkSignUp(PostUserReq postUserReq) {
        // check blank
        if(postUserReq.getName().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NAME;
        if(postUserReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(postUserReq.getBirth().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_BIRTH;
        if(postUserReq.getPhone().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_PHONE;
        if(postUserReq.getGender().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_GENDER;
        if(postUserReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // check form
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

    /** check blank & form when edit user information **/
    public static BaseResponseStatus checkUpdateUser(PatchUserReq patchUserReq) {
        // check blank
        if(patchUserReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(patchUserReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // check form
        String nickname = patchUserReq.getNickname();

        if(nickname.length() < 1 || nickname.length() > 8) return BaseResponseStatus.POST_USERS_INVAlID_NICKNAME;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when create feed **/
    public static BaseResponseStatus checkPostFeed(PostFeedReq postFeedReq) {
        // check blank
        if(postFeedReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(postFeedReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;
        if(postFeedReq.getLocation().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_LOCATION;
        if(postFeedReq.getIsReceiver().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_IS_RECEIVER;

        // check form
        int abo = postFeedReq.getAbo(), rh = postFeedReq.getRh();
        String isReceiver = postFeedReq.getIsReceiver();
        if(abo < 0 || abo > 3) return BaseResponseStatus.POST_FEEDS_INVALID_ABO;
        if(rh < 0 || rh > 1) return BaseResponseStatus.POST_FEEDS_INVALID_RH;
        if(!(isReceiver.equals("T") || isReceiver.equals("F"))) return BaseResponseStatus.POST_FEEDS_INVALID_IS_RECEIVER;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when edit feed **/
    public static BaseResponseStatus checkUpdateFeed(PatchFeedReq patchFeedReq) {
        // check blank
        if(patchFeedReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(patchFeedReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when create comment **/
    public static BaseResponseStatus checkPostComment(PostCommentReq postCommentReq) {
        // check blank
        if(postCommentReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(postCommentReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when create reply **/
    public static BaseResponseStatus checkPostReply(PostReplyReq postReplyReq) {
        // check blank
        if(postReplyReq.getUserId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_USER;
        if(postReplyReq.getFeedId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_FEED;
        if(postReplyReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }
}
