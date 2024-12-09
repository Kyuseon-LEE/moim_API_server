package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class AnnouncementDto {
    private int an_no;
    private String an_type;
    private String an_title;
    private String an_body;
    private String an_owner;
    private String an_reg_date;
    private String an_mod_date;
}
