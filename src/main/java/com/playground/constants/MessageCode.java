package com.playground.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageCode {
  // Common Error
  UNKNOWN("EC0000", "알 수 없는 에러가 발생했습니다.")
  ,CONFIRM("EC0001", "%s 확인해주세요.")
  ,NOT_EXIST_DATA("EC0002", "데이터가 존재하지 않습니다.")
  ,ENCRYPTION_FAILED("EC0003", "암호화에 실패하셨습니다.")
  ,DECRYPTION_FAILED("EC0004", "복호화에 실패하셨습니다.")
  ,INVALID_TOKEN("EC0005", "유효하지 않은 토큰입니다.")
  ,EXPIRED_TOKEN("EC0006", "만료된 토큰입니다.")
  ,ACESS_DENIED_EMAIL("EC0007", "접근 권한이 없는 사용자 요청입니다.")
  ,FAIL_EXCEL_DOWNLOAD("EC0008", "엑셀 다운로드 처리 중 오류가 발생했습니다.")

  // Member Error
  ,ACCESS_NOT_USER("EM0001", "회원 가입 대상이 아닙니다.")
  ,INVALID_USER("EM0002", "회원 정보가 존재하지 않습니다.")
  ,DUPLICATE_USER("EM0003", "이미 가입 되어있는 회원입니다.")
  ,INVALID_USER_ID("EM0004", "ID를 확인해주세요.")
  ,INVALID_NAME("EM0005", "이름을 확인해주세요.")
  ,INVALID_PASSWD("EM0006", "비밀번호를 확인해주세요.")
  ,INVALID_EMAIL("EM0007", "이메일을 확인해주세요.")

  // File Error
  ,NOT_EXIST_FILE("EF0001", "파일이 없습니다.")
  ,NOT_EXIST_FILE_EXT("EF0002", "%s 파일이 없습니다.")
  ,NOT_UPLOAD("EF0003", "%s 업로드 할 수 없습니다.")
  ;

  private final String code;
  private final String msg;

  public String getMessage() {
    return this.msg;
  }

  public String getMessage(Object[] args) {
    return String.format(msg, args);
  }
}