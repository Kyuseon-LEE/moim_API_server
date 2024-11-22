package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class GroupDto {
    private int g_no;
    private String g_name; 
    private String g_info;
    private String g_category;
    private String g_master_id;
    private String g_img_name;
    private int g_max_number;
    private String g_location;
    private int g_good;
    private int g_public;
    private int g_confirm;
    private int g_regist;
    private String g_reg_date;
    private String g_mod_date;
}
