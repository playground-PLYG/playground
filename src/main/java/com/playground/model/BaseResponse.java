package com.playground.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.playground.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> extends BaseDto {
  private String result;

  @JsonInclude(Include.NON_NULL)
  private String errorMessage;

  @JsonInclude(Include.NON_NULL)
  private String resultCode;

  private T data;

  public BaseResponse() {
    this.result = MessageUtils.SUCCESS;
    this.resultCode = "0000";
  }

  public BaseResponse(T data) {
    this.result = MessageUtils.SUCCESS;
    this.resultCode = "0000";
    this.data = data;
  }

  public BaseResponse(String errorMessage) {
    this.result = MessageUtils.FAIL;
    this.resultCode = "9999";
    this.errorMessage = errorMessage;
  }
}
