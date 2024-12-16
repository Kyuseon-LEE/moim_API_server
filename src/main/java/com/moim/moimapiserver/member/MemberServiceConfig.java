package com.moim.moimapiserver.member;

public interface MemberServiceConfig {

    public static int ALREADY_EXIST_ID = -1;
    public static int DATABASE_ERROR = -2;
    public static int AVAILABLE_ID = -3;
    public static int SIGNUP_SUCCESS = 0;
    public static int SIGNUP_FAIL = 1;
    public static int LOGIN_SUCCESS = 1;
    public static int LOGIN_FAIL = 2;
    public static int FAIL_MEMBER_INFO = 3;
    public static int SUCCESS_MEMBER_UPDATE = 4;
    public static int FAIL_MEMBER_UPDATE = 5;
    public static int SUCCESS_CATEGORY_INSERT = 6;
    public static int FAIL_CATEGORY_INSERT = 7;
    public static int SUCCESS_SOCIAL_SIGNUP = 8;
    public static int FAIL_SOCIAL_SIGNUP = 9;
    public static int NOT_EXIST_SOCIAL_MEMBER = 10;
    public static int EXIST_SOCIAL_MEMBER = 11;
    public static int CORRECT_USER_FOR_FIND_PASSWORD = 12;
    public static int FAIL_USER_FOR_FIND_PASSWORD = 13;
    public static int SUCCESS_UPDATE_TEMPORARY_PASSWORD = 14;
    public static int FAIL_UPDATE_TEMPORARY_PASSWORD = 15;
    public static int SUCCESS_UPDATE_PASSWORD = 16;
    public static int FAIL_UPDATE_PASSWORD = 17;
    public static int OKAY_MID = 18;
    public static int NOT_OKAY_MID = 19;
    public static int SUCCESS_UPDATE_PROFILE_IMAGE = 20;
    public static int FAIL_UPDATE_PROFILE_IMAGE = 21;

}
