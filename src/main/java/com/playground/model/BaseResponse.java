package com.playground.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.playground.constants.CommonConstants;
import com.playground.exception.BizException;
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
    this.result = CommonConstants.SUCCESS;
    this.resultCode = CommonConstants.SUCCESS_CODE;
  }

  public BaseResponse(T data) {
    this.result = CommonConstants.SUCCESS;
    this.resultCode = CommonConstants.SUCCESS_CODE;
    this.data = data;
  }

  public BaseResponse(BizException e) {
    this.result = CommonConstants.FAIL;
    this.resultCode = e.getErrCode().getCode();
    this.errorMessage = e.getMessage();
  }

  public BaseResponse(String errorMessage) {
    this.result = CommonConstants.FAIL;
    this.resultCode = CommonConstants.FAIL_CODE;
    this.errorMessage = errorMessage;
  }
}
