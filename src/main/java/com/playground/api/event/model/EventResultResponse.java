package com.playground.api.event.model;

import java.time.LocalDateTime;
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
public class EventResultResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트일련번호")
  private Integer eventSerial;

  @Schema(description = "총포인트 값")
  private Integer totalPointValue;

  @Schema(description = "당첨포인트값")
  private String memberNm;

  @Schema(description = "맴버ID")
  private String memberId;

  @Schema(description = "맴버전화번호")
  private String memberTelno;

  @Schema(description = "당첨포인트값")
  private Integer przwinPointVal;

  @Schema(description = "이벤트참여일시")
  private LocalDateTime eventPartcptnDate;

  @Schema(description = "이벤트참여여부")
  private String eventPrzwinAlter;

}
