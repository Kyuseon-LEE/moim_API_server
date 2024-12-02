package com.moim.moimapiserver.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

}