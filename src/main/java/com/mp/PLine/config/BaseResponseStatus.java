package com.mp.PLine.config;

import lombok.Getter;

/**
 * Error code
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : Request success
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /** 2000 : Request error **/
    /* Common */
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    EMPTY_ACCESS_TOKEN(false, 2004, "ACCESS TOKEN을 입력해주세요."),
    WRONG_SOCIAL_ID_TOKEN(false, 2005, "TOKEN의 SOCIAL ID를 확인해주세요."),

    /* User */
    POST_USERS_EMPTY_NAME(false, 2010, "이름을 입력해주세요."),
    POST_USERS_EMPTY_NICKNAME(false, 2011, "닉네임을 입력해주세요."),
    POST_USERS_EMPTY_BIRTH(false, 2012, "생년월일을 입력해주세요."),
    POST_USERS_EMPTY_PHONE(false, 2013, "전화번호를 입력해주세요."),
    POST_USERS_EMPTY_GENDER(false, 2014, "성별을 입력해주세요."),
    POST_USERS_EMPTY_LOCATION(false, 2015, "위치를 입력해주세요."),

    POST_USERS_INVAlID_NICKNAME(false, 2020, "닉네임 길이를 확인해주세요."),
    POST_USERS_INVAlID_PHONE(false, 2021, "올바르지 않은 전화번호 형식입니다."),
    POST_USERS_INVALID_BIRTH(false, 2022, "올바르지 않은 생년월일 형식입니다."),
    POST_USERS_INVALID_GENDER(false, 2023, "올바르지 않은 성별 형식입니다."),
    POST_USERS_INVALID_ABO(false, 2024, "올바르지 않은 ABO 혈액형 형식입니다."),
    POST_USERS_INVALID_RH(false, 2025, "올바르지 않은 RH 혈액형 형식입니다."),
    POST_USERS_INVALID_PROFILE(false, 2026, "올바르지 않은 프로필 사진 형식입니다."),

    EXIST_USER(false,2027,"이미 존재하는 유저입니다."),
    INVALID_USER(false,2028,"존재하지 않는 유저입니다."),

    /* Feed */
    POST_FEEDS_EMPTY_USER(false, 2030, "유저 아이디를 입력해주세요."),
    POST_FEEDS_EMPTY_CONTEXT(false, 2031, "본문을 입력해주세요."),
    POST_FEEDS_EMPTY_LOCATION(false, 2032, "장소를 입력해주세요."),
    POST_FEEDS_EMPTY_IS_RECEIVER(false, 2033, "수혈 & 공혈 여부를 입력해주세요."),
    POST_FEEDS_EMPTY_FEED(false, 2034, "게시글 아이디를 입력해주세요."),

    POST_FEEDS_INVALID_ABO(false, 2040, "올바르지 않은 ABO 혈액형 형식입니다."),
    POST_FEEDS_INVALID_RH(false, 2041, "올바르지 않은 RH 혈액형 형식입니다."),
    POST_FEEDS_INVALID_IS_RECEIVER(false, 2042, "올바르지 않은 수혈 & 공혈 형식입니다."),
    POST_FEEDS_INVALID_FEED(false, 2043, "올바르지 않은 게시물 경로입니다."),


    INVALID_FEED(false,2045,"존재하지 않는 게시물입니다."),
    INVALID_COMMENT(false,2046,"존재하지 않는 댓글입니다."),
    INVALID_REPLY(false,2047,"존재하지 않는 답글입니다."),

    /* Admin */
    POST_CERTIFICATION_EMPTY_NAME(false, 2050, "이름을 입력해주세요."),
    POST_CERTIFICATION_EMPTY_NUM(false, 2051, "헌혈 번호를 입력해주세요."),
    POST_CERTIFICATION_EMPTY_DATE(false, 2052, "헌혈 날짜를 입력해주세요."),
    POST_CERTIFICATION_EMPTY_IMAGE(false, 2053, "헌혈 증서를 업로드해주세요."),
    POST_CERTIFICATION_INVALID_NAME(false, 2054, "본인이 한 헌혈 증서만 등록 가능합니다."),
    INVALID_ADMIN_KEY(false, 2055, "올바르지 못한 어드민 키입니다."),
    NOT_EXIST_ADMIN_ACCOUNT(false, 2056, "존재하지 않는 어드민입니다."),

    /* Report */
    INVALID_REPORT(false,2060,"유효하지 않은 신고입니다."),
    INVALID_REPORT_USER(false,2061,"신고하려는 유저가 존재하지 않는 유저입니다."),
    INVALID_REPORT_SAME_USER(false,2062,"자기 자신은 신고할 수 없습니다."),
    INVALID_EXIST_REPORT(false,2063, "이미 신고가 완료되었습니다."),
    INVALID_NON_EXIST_REPORT(false,2064, "신고가 접수되지 않았습니다."),
    INVALID_REPORT_CATEGORY(false,2065, "잘못된 카테고리 신고입니다."),

    /**
     * 3000 : Response error
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server error
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    WRONG_STATUS_CODE(false, 4020, "존재하지 않은 상태코드입니다");


    // BaseResponseStatus Mapping
    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public static BaseResponseStatus getBaseStatusByCode(int code) throws BaseException {
        for (BaseResponseStatus baseStatus: BaseResponseStatus.values()) {
            if (baseStatus.code == code) {
                return baseStatus;
            }
        }
        throw new BaseException(BaseResponseStatus.WRONG_STATUS_CODE);
    }
}
