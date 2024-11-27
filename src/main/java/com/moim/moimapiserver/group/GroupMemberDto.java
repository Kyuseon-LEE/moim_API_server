package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class GroupMemberDto {
	
	private int g_m_no;     
    private int g_no;       
    private int m_no;       
    private int g_m_role;   
    private String g_reg_date; 
    private String g_mod_date; 
    
    private String m_nickname;       // 추가된 필드
    private String m_profile_img;   // 추가된 필드
}
