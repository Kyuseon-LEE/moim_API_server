package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class UserJoinGroupDto {
    private int g_no;
    private String g_reg_date;
    private String g_name;
    private String g_m_role;
    private String g_category;
    private String g_location;
}
