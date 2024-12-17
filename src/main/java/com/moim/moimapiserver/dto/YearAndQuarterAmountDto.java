package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class YearAndQuarterAmountDto {
    private String year;
    private String quarter;
    private int total_sales;
}
