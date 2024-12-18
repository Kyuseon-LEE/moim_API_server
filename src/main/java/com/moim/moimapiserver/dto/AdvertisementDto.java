package com.moim.moimapiserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdvertisementDto {
    private int ad_no;
    private String ad_name;
    private String ad_img;
    private String ad_type;
    private String ad_owner;
    private String ad_advertiser;
    private String ad_link;
    private Date ad_reg_date;
    private Date ad_mod_date;
    private Date ad_start_date;
    private Date ad_end_date;
    private Date ad_approval_date;
    private int ad_status;
}
