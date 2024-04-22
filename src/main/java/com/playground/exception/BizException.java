package com.playground.exception;

import java.io.Serial;
import com.playground.constants.MessageCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -968869927329692091L;

  private final MessageCode errCode;

  public BizException(MessageCode errCode) {
    super(errCode.getMessage());
    this.errCode = errCode;
  }

  public BizException(MessageCode errCode, String msg) {
    super(msg);
    this.errCode = errCode;
  }

  public BizException(MessageCode errCode, String[] args) {
    super(errCode.getMessage(args));
    this.errCode = errCode;
  }

  public BizException(String msg) {
    super(msg);
    this.errCode = MessageCode.UNKNOWN;
  }

  public BizException() {
    super(MessageCode.UNKNOWN.getMessage());
    this.errCode = MessageCode.UNKNOWN;
  }
}
