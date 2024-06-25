package com.playground.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.playground.constants.MessageCode;
import com.playground.model.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(UnsupportedJwtException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public BaseResponse<Void> unsupportedJwtException(Exception e) {
    return new BaseResponse<>(new BizException(MessageCode.UNSUPPROTED_TOKEN, MessageCode.UNSUPPROTED_TOKEN.getMessage()));
  }

  @ExceptionHandler(MalformedJwtException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public BaseResponse<Void> malformedJwtException(Exception e) {
    return new BaseResponse<>(new BizException(MessageCode.INVALID_TOKEN, MessageCode.INVALID_TOKEN.getMessage()));
  }

  @ExceptionHandler(ExpiredJwtException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public BaseResponse<Void> expiredJwtException(Exception e) {
    return new BaseResponse<>(new BizException(MessageCode.EXPIRED_TOKEN, MessageCode.EXPIRED_TOKEN.getMessage()));
  }
  
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public BaseResponse<Void> illegalArgumentException(Exception e) {
    return new BaseResponse<>(new BizException(MessageCode.WRONG_TOKEN, MessageCode.WRONG_TOKEN.getMessage()));
  }
  
  @ExceptionHandler(SignatureException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public BaseResponse<Void> signatureException(Exception e) {
    return new BaseResponse<>(new BizException(MessageCode.NOT_SIGNATRUE_TOKEN, MessageCode.NOT_SIGNATRUE_TOKEN.getMessage()));
  }

  @ExceptionHandler(SecurityException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Void> securityException(Exception e) {
    return new BaseResponse<>("SignatureException");
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public BaseResponse<Void> httpRequestMethodNotSupportedException(Exception e) {
    return new BaseResponse<>("HttpRequestMethodNotSupportedException");
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse<Void> noHandlerFoundException(Exception e) {
    return new BaseResponse<>("NoHandlerFoundException");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Void> methodArgumentValidException(MethodArgumentNotValidException e) {
    ObjectError objectError = e.getBindingResult().getAllErrors().stream().findFirst().orElse(new ObjectError("", "파라메터가 올바르지 않습니다."));

    return new BaseResponse<>(objectError.getDefaultMessage());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Void> missingServletRequestParameterException(MissingServletRequestParameterException e) {
    String parameterName = e.getParameterName();
    String message = String.format("'%s'은(는) 필수 값 입니다.", parameterName);
    ObjectError objectError = new ObjectError(parameterName, message);

    return new BaseResponse<>(objectError.getDefaultMessage());
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
  public BaseResponse<Void> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    /*
     * TODO maxUploadSize 조회 안되고 -1로 나오는 현상 파악 long maxUploadSize = e.getMaxUploadSize(); String message = String.format("파일 업로드 용량을 초과했습니다. %s", maxUploadSize); ObjectError objectError = new ObjectError("maxUploadSize", message);
     *
     * return new BaseResponse<>(objectError.getDefaultMessage());
     */
    return new BaseResponse<>("파일 업로드 용량을 초과했습니다. %s");
  }

  @ExceptionHandler(BizException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse<Void> bizException(BizException e) {
    return new BaseResponse<>(e);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse<Void> internalServerErrorException(Exception e) {
    return new BaseResponse<>("InternalServerError");
  }
}
