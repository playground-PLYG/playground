package com.playground.aop;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.playground.model.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice(basePackages = "com.playground.api")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

    int status = servletResponse.getStatus();
    HttpStatus resolve = HttpStatus.resolve(status);

    if (resolve == null) {
      return body;
    }
    
    if (body instanceof SseEmitter) {
      return body;
    }

    if (response.getHeaders().getContentType() != null && StringUtils.equals(String.valueOf(response.getHeaders().getContentType()),
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
      return body;

    }

    if (resolve.is2xxSuccessful()) {
      return new BaseResponse<>(body);
    }

    return body;
  }
}
