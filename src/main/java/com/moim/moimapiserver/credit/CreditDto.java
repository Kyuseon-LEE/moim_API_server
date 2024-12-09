package com.moim.moimapiserver.credit;

import com.moim.moimapiserver.member.MemberDto;
import lombok.Data;

@Data
public class CreditDto {
    private int pay_no;
    private int m_no;
    private String pay_prod_name;
    private int pay_period;
    private int pay_amount;
    private String pay_method;
    private int pay_status;
    private String pay_reg_date;
    private String pay_mod_date;
    private String pay_expired_date;
    private MemberDto memberDto;
}

