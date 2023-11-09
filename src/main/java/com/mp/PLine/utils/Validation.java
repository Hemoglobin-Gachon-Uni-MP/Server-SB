package com.mp.PLine.utils;

import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.req.PatchFeedReq;
import com.mp.PLine.src.feed.dto.req.PostCommentReq;
import com.mp.PLine.src.feed.dto.req.PostFeedReq;
import com.mp.PLine.src.feed.dto.req.PostReplyReq;
import com.mp.PLine.src.member.dto.req.PostMemberReq;
import com.mp.PLine.src.myPage.dto.req.PatchMemberReq;

public class Validation {
    /** check blank & form when sign-up **/
    public static BaseResponseStatus checkSignUp(PostMemberReq postMemberReq) {
        // check blank
        if(postMemberReq.getName().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NAME;
        if(postMemberReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(postMemberReq.getBirth().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_BIRTH;
        if(postMemberReq.getPhone().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_PHONE;
        if(postMemberReq.getGender().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_GENDER;
        if(postMemberReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // check form
        String nickname = postMemberReq.getNickname();
        String phone = postMemberReq.getPhone();
        String birth = postMemberReq.getBirth();
        String gender = postMemberReq.getGender();
        int abo = postMemberReq.getAbo(), rh = postMemberReq.getRh();

        if(nickname.length() < 1 || nickname.length() > 8) return BaseResponseStatus.POST_USERS_INVAlID_NICKNAME;
        if(!ValidationRegex.isRegexPhone(phone)) return BaseResponseStatus.POST_USERS_INVAlID_PHONE;
        if(!ValidationRegex.isRegexBirth(birth)) return BaseResponseStatus.POST_USERS_INVALID_BIRTH;
        if(!(gender.equals("M") || gender.equals("F"))) return BaseResponseStatus.POST_USERS_INVALID_GENDER;
        if(abo < 0 || abo > 3) return BaseResponseStatus.POST_USERS_INVALID_ABO;
        if(rh < 0 || rh > 1) return BaseResponseStatus.POST_USERS_INVALID_RH;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when edit user information **/
    public static BaseResponseStatus checkUpdateMember(PatchMemberReq patchMemberReq) {
        // check blank
        if(patchMemberReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(patchMemberReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // check form
        String nickname = patchMemberReq.getNickname();

        if(nickname.length() < 1 || nickname.length() > 8) return BaseResponseStatus.POST_USERS_INVAlID_NICKNAME;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when create feed **/
    public static BaseResponseStatus checkPostFeed(PostFeedReq postFeedReq) {
        // check blank
        if(postFeedReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;
        if(postFeedReq.getLocation().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_LOCATION;

        // check form
        int abo = postFeedReq.getAbo(), rh = postFeedReq.getRh();
        if(abo < 0 || abo > 3) return BaseResponseStatus.POST_FEEDS_INVALID_ABO;
        if(rh < 0 || rh > 1) return BaseResponseStatus.POST_FEEDS_INVALID_RH;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when edit feed **/
    public static BaseResponseStatus checkUpdateFeed(PatchFeedReq patchFeedReq) {
        // check blank
        if(patchFeedReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when create comment **/
    public static BaseResponseStatus checkPostComment(PostCommentReq postCommentReq) {
        // check blank
        if(postCommentReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }

    /** check blank & form when create reply **/
    public static BaseResponseStatus checkPostReply(PostReplyReq postReplyReq) {
        // check blank
        if(postReplyReq.getFeedId() == null) return BaseResponseStatus.POST_FEEDS_EMPTY_FEED;
        if(postReplyReq.getContext().isBlank()) return BaseResponseStatus.POST_FEEDS_EMPTY_CONTEXT;

        return BaseResponseStatus.SUCCESS;
    }
}
