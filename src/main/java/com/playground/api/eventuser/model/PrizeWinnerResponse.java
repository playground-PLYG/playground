package com.playground.api.eventuser.model;

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
public class PrizeWinnerResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "회원명")
  private String memberName;

  @Schema(description = "회원아이디")
  private String memberId;

  @Schema(description = "포인트지급단위값")
  private Integer pointPaymentUnitValue;

}
