package com.playground.api.eventuser.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "EventListRequest", description = "이벤트 목록 요청")
@EqualsAndHashCode(callSuper = true)
@Getter
public class EventUserListRequest extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트명")
  private String eventName;

  @Schema(description = "진행상태")
  private String progrsSttus;
  // e : 종료 , i : 진행중

}
