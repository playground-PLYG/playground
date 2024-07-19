package com.playground.api.eventuser.model;

import java.util.ArrayList;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "EventWinningResponse", description = "응모형 이벤트 당첨자 응답")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventPrizeWinnerResponse extends BaseDto {
  private static final long serialVersionUID = 1L;
  
  @Schema(description = "이벤트당첨여부")
  private String eventPrizeAt;
  
  @Schema(description = "당첨포인트값")
  private Integer przwinPointValue;
  
  @Builder.Default
  private List<PrizeWinnerResponse> prizeWinner = new ArrayList<>();


}
