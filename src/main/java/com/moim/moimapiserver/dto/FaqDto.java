package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class FaqDto {
    private int faq_no;
    private String faq_category;
    private String faq_title;
    private String faq_body;
    private String faq_register_admin;
    private String faq_reg_date;
    private String faq_mod_date;
}
