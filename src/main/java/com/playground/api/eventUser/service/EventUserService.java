package com.playground.api.eventUser.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.playground.api.eventUser.model.EventUserListRequest;
import com.playground.api.eventUser.model.EventUserListResponse;
import com.playground.api.eventUser.repository.EventUserRepository;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.service.MberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventUserService {

  private final EventUserRepository eventUserRepository;
  private final MberService mberService;

  /** 사용자 이벤트 목록 조회 */
  public List<EventUserListResponse> getEventList(EventUserListRequest req, String token) {

    String mberId = "";
    try {
      MberInfoResponse member = mberService.getMyInfo(token);
      mberId = member.getMberId();
      
    } catch (Exception e) {
      //memberId가 없는경우 
    }
    List<EventUserListResponse> result = eventUserRepository.getEventList(req.getEventName(), req.getProgrsSttus(), mberId);

    return result;
  }

}
