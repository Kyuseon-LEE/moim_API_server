package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class RecentStatisticsDto {
    private String period;
    private int count;
}
