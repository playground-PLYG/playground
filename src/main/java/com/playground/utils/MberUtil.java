package com.playground.utils;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.common.net.HttpHeaders;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.repository.MberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MberUtil {
  private final MberRepository mberRepository;

  /**
   * 로그인한 사용자 정보 조회. 로그인 하지 않거나 토큰이 유효하지 않으면 null return
   *
   * @return MberInfoResponse | null
   */
  public Optional<MberInfoResponse> getMber() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (!ObjectUtils.isEmpty(token)) {
      if (JwtTokenUtil.isValidToken(token)) {
        try {
          MberInfoResponse member = JwtTokenUtil.autholriztionCheckUser(token);

          return Optional.of(mberRepository.findByIdDetail(member.getMberId()));
        } catch (Exception e) {
          return Optional.empty();
        }
      }

      return Optional.empty();
    } else {
      return Optional.empty();
    }
  }
}
