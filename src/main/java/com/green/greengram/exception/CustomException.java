package com.green.greengram.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

// RuntimeException + ErrorCode를 implements한 객체 주소값을 담을 수 있는 기능

@Getter
@RequiredArgsConstructor
@ToString
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
