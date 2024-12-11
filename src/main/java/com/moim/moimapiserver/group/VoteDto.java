package com.moim.moimapiserver.group;

import lombok.Data;

@Data
public class VoteDto {
	
	private int ev_no;
	private int e_no;
	private int m_no;
	private int vote_status;
    private String vote_date;
}
