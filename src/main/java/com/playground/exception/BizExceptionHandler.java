package com.playground.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.playground.model.BaseResponse;

@ControllerAdvice
public class BizExceptionHandler {

  @ExceptionHandler(BizException.class)
  protected ResponseEntity<BaseResponse<Void>> customException(BizException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(e));
  }
}
