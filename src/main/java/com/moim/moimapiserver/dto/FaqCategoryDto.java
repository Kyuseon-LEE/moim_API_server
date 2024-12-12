package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class FaqCategoryDto {
    private int faq_category_no;
    private String faq_category;
    private String faq_category_reg_date;
    private String faq_category_mod_date;
}
