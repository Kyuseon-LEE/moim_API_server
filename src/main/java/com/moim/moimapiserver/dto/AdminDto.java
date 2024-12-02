package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class AdminDto {
    private int a_no;
    private String a_id;
    private String a_pw;
    private String a_name;
    private String a_mail;
    private String a_phone;
    private int a_approval;
    private int a_authority;
    private int a_part;
    private int a_position;
    private int a_is_active;
    private String a_reg_date;
    private String a_mod_date;
    private String a_approval_date;
    private String a_authority_date;
    private String a_is_active_date;
}
