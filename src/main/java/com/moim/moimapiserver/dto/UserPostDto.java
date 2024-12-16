package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class UserPostDto {
    private int p_no;
    private int g_no;
    private String p_text;
    private String p_img;
    private String p_reg_date;
    private String p_mod_date;
}
