package com.playground.api.event.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "PointPaymentResponse", description = "이벤트포인트지급")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PointPaymentResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트일련번호")
  private Integer eventSerial;

  @Schema(description = "포인트지급일련번호")
  private Integer pointPymntSerial;

  @Schema(description = "포인트지급단위값")
  private Integer pointPaymentUnitValue;

  @Schema(description = "고정포인트지급자수")
  private Integer fixingPointPayrCount;

  @Schema(description = "고정포인트값")
  private Integer fixingPointValue;
}
