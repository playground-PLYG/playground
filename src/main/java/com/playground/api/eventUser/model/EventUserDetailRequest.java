package com.playground.api.eventUser.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "EventUserDetailRequest", description = "이벤트 상세 조회 요청")
@EqualsAndHashCode(callSuper = true)
@Getter
public class EventUserDetailRequest extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트일련번호")
  private Integer eventSerial;
}
