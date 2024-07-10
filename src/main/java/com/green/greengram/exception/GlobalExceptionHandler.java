package com.green.greengram.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
/*
    Advice라는 단어가 보이면 AOP라고 인식하면 됨
    AOP(Aspect Oriented Programming, 관점지향 프로그래밍)
    Exception을 잡아낸다.(모두 or 개별 가능)
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //우리가 커스텀한 예외가 발생되었을 경우 캐치
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleException(CustomException e) {
        log.error("CustomException - handlerException: {}", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    //Validation 예외가 발생되었을 경우 캐치
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, ex);
    }

    //이외의 모든 예외 캐치
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("Exception - handlerException: {}", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, BindException e) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, e));
    }

    private MyErrorResponse makeErrorResponse(ErrorCode errorCode, BindException e) {
        return MyErrorResponse.builder()
                .statusCode(errorCode.getHttpStatus())
                .resultMsg(errorCode.getMessage())
                .resultData(errorCode.name()) //enum의 이름
                .valids(e == null ? null : getValidationError(e)) //validation 에러 메시지를 정리하는 메소드
                .build();
    }

    private List<MyErrorResponse.ValidationError> getValidationError(BindException e) {
        List<MyErrorResponse.ValidationError> list = new ArrayList();

        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        for(FieldError fieldError : fieldErrorList) {
            MyErrorResponse.ValidationError validError = MyErrorResponse.ValidationError.of(fieldError);
            list.add(validError);
            //list.add(MyErrorResponse.ValidationError.of(fieldError));
        }
        return list;
    }

}
