package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class PostDto {
	private int p_no;
	private int g_no;
	private int m_no;
	private String p_text;
	private String p_img;
	private String p_reg_date;
	private String p_mod_date;
	
	private String m_nickname;
	private String m_profile_img;
	private int g_m_role;
	private String m_mail;
	private String m_category;
	private String g_reg_date;
}
