package com.playground.api.eventUser.service;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.common.net.HttpHeaders;
import com.playground.api.eventUser.model.EventUserListRequest;
import com.playground.api.eventUser.model.EventUserListResponse;
import com.playground.api.eventUser.repository.EventUserRepository;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.constants.CacheType;
import com.playground.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventUserService {

  private final EventUserRepository eventUserRepository;

  /** 사용자 이벤트 목록 조회 */
  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "members", key = "#p0", unless = "#result == null")
  @Transactional(readOnly = true)
  public List<EventUserListResponse> getEventList(EventUserListRequest req) {

    String mberId = "";

    try {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
      String token = request.getHeader(HttpHeaders.AUTHORIZATION);
      MberInfoResponse member = JwtTokenUtil.autholriztionCheckUser(token);
      if (!ObjectUtils.isEmpty(token) && !ObjectUtils.isEmpty(member)) {
        mberId = member.getMberId();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<EventUserListResponse> result = eventUserRepository.getEventList(req.getEventName(), req.getProgrsSttus(), mberId);

    return result;
  }

}
