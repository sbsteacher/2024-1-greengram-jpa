package com.green.greengram.common.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResultDto<T> {
    private HttpStatus statusCode;
    private String resultMsg;
    private T resultData;
}

/*
{
    "statusCode": 200,
    "resultMsg": "통신 완료",
    " resultData": {
        "name": "홍길동"
    }
}


 */