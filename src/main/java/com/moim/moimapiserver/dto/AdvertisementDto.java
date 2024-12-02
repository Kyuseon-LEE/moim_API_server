package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class AdvertisementDto {
    private int ad_no;
    private String ad_name;
    private String ad_img;
    private String ad_type;
    private String ad_owner;
    private String ad_advertiser;
    private String ad_link;
    private String ad_reg_date;
    private String ad_mod_date;
    private String ad_start_date;
    private String ad_end_date;
    private String ad_approval_date;
    private int ad_status;
}
