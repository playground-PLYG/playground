package com.playground.utils;

import org.apache.commons.lang3.StringUtils;
import com.playground.constants.PlaygroundConstants;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class MaskingUtil {

  /**
   * 주민/외국인등록번호 뒷자리 둘째자리부터 마스킹 올바르지 않은 문자열의 경우 빈 문자열("")을 반환
   *
   * @param number - 주민/외국인등록번호 문자열
   * @return String - 뒷자리 둘째자리 부터 마스킹 처리된 문자열
   */
  public static String residentForeignerRegistrationNumber(String number) {
    if (StringUtils.isBlank(number) || !number.matches(PlaygroundConstants.RegexPattern.RESIDENT_FOREIGNER_REGISTRATION_NUMBER)) {
      return "";
    }

    return number.replaceAll("^(\\d{6}-*\\d)\\d{6}$", "$1******");
  }

  /**
   * 문자열의 처음 1자리, 마지막 1자리를 제외한 모든 문자열 마스킹 두자리 이하인 경우 마스킹 처리없이 그대로 반환
   *
   * @param str - 마스킹 처리 할 문자열
   * @return String - 마스킹 처리된 문자열
   */
  public static String withoutFirstAndLast(String str) {
    if (StringUtils.isBlank(str)) {
      return "";
    }

    return str.replaceAll("(?<=.).(?=.)", "*");
  }


  /**
   * 이름 마스킹
   *
   * @param str - 마스킹 처리 할 문자열
   * @return String - 마스킹 처리된 문자열
   */
  public static String name(String str) {
    if (StringUtils.isBlank(str)) {
      return "";
    }

    if (StringUtils.length(str) > 2) {
      return withoutFirstAndLast(str);
    } else {
      return str.replaceAll("(?<=.{1}).", "*");
    }
  }
}
