package com.moim.moimapiserver.credit;

import lombok.Data;

@Data
public class CreditDto {
    private int pay_no;
    private int m_no;
    private String pay_prod_name;
    private int pay_period;
    private int pay_amount;
    private String pay_method;
    private String pay_reg_date;
    private String pay_mod_date;

}
