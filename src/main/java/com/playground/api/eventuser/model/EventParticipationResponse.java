package com.playground.api.eventuser.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "EventParticipationResponse", description = "이벤트 참여 응답")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventParticipationResponse extends BaseDto {
  private static final long serialVersionUID = 1L;
  
  @Schema(description = "이벤트당첨여부")
  private String eventPrizeAt;
  
  @Schema(description = "당첨포인트값")
  private Integer przwinPointValue;

}
