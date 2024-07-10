package com.green.greengram.exception;

import com.green.greengram.common.model.MyResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@SuperBuilder
public class MyErrorResponse extends MyResponse<String> {
    private final List<ValidationError> valids;

    //Validation 에러가 발생시 해당하는 에러메시지를 표시할 때 사용하는 객체
    //inner class는 static을 붙여주면 성능이 좋아진다.
    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        private final String field; //validation 에러가 발생된 멤버필드명
        private final String message; //validation 에러 메시지

        //ValidationError 객체 생성을 담당하는 메소드
        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
