package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class CustomerInquiriesDto {
    private int csi_no;
    private String csi_title;
    private int csi_category;
    private String csi_body;
    private String csi_request_user;
    private int csi_status;
    private String csi_attach_file1;
    private String csi_attach_file2;
    private String csi_attach_file3;
    private String csi_attach_file4;
    private String csi_request_date;
    private String csi_response_date;
    private String csi_response_admin;
    private String csi_response_body;
}
