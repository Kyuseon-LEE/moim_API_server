package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class EventDto {

	private int e_no;
	private int g_no;
    private String e_title;
    private String e_text;
    private String e_start_date;
    private String e_time;
    private String e_location;
    private String e_reg_date;
    private String e_mod_date;
}
