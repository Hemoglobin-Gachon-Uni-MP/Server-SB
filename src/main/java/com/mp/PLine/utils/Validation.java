package com.mp.PLine.utils;

import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.member.dto.PostUserReq;
import com.mp.PLine.src.myPage.dto.PatchUserReq;

public class Validation {
    /** 회원 가입 시 비어있는 값이 있는지 확인 **/
    public static BaseResponseStatus checkSignUp(PostUserReq postUserReq) {
        // 빈 칸 확인
        if(postUserReq.getName().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NAME;
        if(postUserReq.getNickname().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_NICKNAME;
        if(postUserReq.getBirth().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_BIRTH;
        if(postUserReq.getPhone().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_PHONE;
        if(postUserReq.getGender().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_GENDER;
        if(postUserReq.getAbo().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_ABO;
        if(postUserReq.getRh().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_RH;
        if(postUserReq.getLocation().isBlank()) return BaseResponseStatus.POST_USERS_EMPTY_LOCATION;

        // 형식
        String nickname = postUserReq.getNickname();
        String phone = postUserReq.getPhone();
        String birth = postUserReq.getBirth();
        String gender = postUserReq.getGender();
        String abo = postUserReq.getAbo(), rh = postUserReq.getRh();
        int profileImg = postUserReq.getProfileImg();

        if(nickname.length() < 1 || nickname.length() > 8) return BaseResponseStatus.POST_USERS_INVAlID_NICKNAME;
        if(!ValidationRegex.isRegexPhone(phone)) return BaseResponseStatus.POST_USERS_INVAlID_PHONE;
        if(!ValidationRegex.isRegexBirth(birth)) return BaseResponseStatus.POST_USERS_INVALID_BIRTH;
        if(!(gender.equals("M") || gender.equals("F"))) return BaseResponseStatus.POST_USERS_INVALID_GENDER;
        if(!(abo.equals("A") || abo.equals("B") || abo.equals("O") || abo.equals("AB"))) return BaseResponseStatus.POST_USERS_INVALID_ABO;
        if(!(rh.equals("Rh+") || rh.equals("Rh-"))) return BaseResponseStatus.POST_USERS_INVALID_RH;
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

}
