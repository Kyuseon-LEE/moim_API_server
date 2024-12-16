package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class UserGenderStatisticsDto {
    private String gender;
    private int count;
}
