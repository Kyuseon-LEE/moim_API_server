package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class ChatDto {
	
	private int c_no;
	private int g_no;
	private int m_no;
    private String c_arrive_id;
    private String c_depart_id;
    private String c_content;
    private String c_img_url;
    private String c_reg_date;
    private String c_mod_date;
    private int em_no;
    
    private String m_nickname;
    
    @Override
    public String toString() {
        return "ChatDto [g_no=" + g_no + ", m_no=" + m_no + ", c_content=" + c_content + "]";
    }
	
}

