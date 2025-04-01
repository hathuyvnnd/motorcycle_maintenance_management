package com.example.exception;

import com.example.dto.reponse.ApiReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //
    //When encounted a RuntimeException, It will here
    ResponseEntity<ApiReponse> handlingException(Exception exception){
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiReponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiReponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiReponse> handlingMethodArgumentNotValidException( MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
        errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e){
            
        }
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setCode(errorCode.getCode());
        apiReponse.setResult(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiReponse);
    }
    @ExceptionHandler(value = AppException.class) //
    ResponseEntity<ApiReponse> handlingRuntimeException(AppException exception){
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setCode(exception.getErrorCode().getCode());
        apiReponse.setMessage(exception.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(apiReponse);
    }

}
