package com.moim.moimapiserver.dto;

import lombok.Data;

@Data
public class TotalAmountDto {
    private String period;
    private int total_sales;
    private double percentage_change;
}
