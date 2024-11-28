package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class CommentDto {
	
	private int co_no;
	private int g_no;
	private int p_no;
	private int m_no;
	private String co_text;
	private String co_reg_date;
	private String co_mod_date;
	
	private String m_nickname;
}
