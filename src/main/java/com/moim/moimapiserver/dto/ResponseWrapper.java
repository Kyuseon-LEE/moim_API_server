package com.moim.moimapiserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseWrapper<T> {
    private T data;
    private String status;
    private String message;
}
