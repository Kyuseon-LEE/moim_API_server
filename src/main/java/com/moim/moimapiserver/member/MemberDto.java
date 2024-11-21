package com.moim.moimapiserver.member;

import lombok.Data;

@Data
public class MemberDto {
    private int m_no;
    private String m_kakao_id;
    private String m_google_id;
    private String m_profile_img;
    private String m_id;
    private String m_pw;
    private String m_name;
    private String m_nickname;
    private String m_phone;
    private String m_gender;
    private String m_age;
    private String m_address;
    private String m_mail;
    private String m_category;
    private int m_grade;
    private int m_status;
    private String m_reg_date;
    private String m_mod_date;
    private String m_start_membership;
    private String m_end_membership;
}
