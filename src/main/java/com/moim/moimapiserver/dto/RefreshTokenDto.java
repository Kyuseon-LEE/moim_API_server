package com.moim.moimapiserver.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshTokenDto {
    private int rt_no;
    private String a_id;
    private String refreshToken;
    private LocalDateTime expires_at;
    private LocalDateTime created_at;
}
