package com.green.greengram.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    INCORRECT_ID_PW(HttpStatus.BAD_REQUEST, "아이디, 비밀번호를 확인해 주세요."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
