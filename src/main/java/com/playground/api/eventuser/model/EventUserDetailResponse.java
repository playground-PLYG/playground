package com.playground.api.eventuser.model;

import java.time.LocalDateTime;
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

@Schema(name = "EventUserDetailResponse", description = "이벤트 상세 응답")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventUserDetailResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트일련번호")
  private Integer eventSerial;

  @Schema(description = "이벤트명")
  private String eventName;

  @Schema(description = "컨테츠내용")
  private String contents;

  @Schema(description = "이벤트시작일시")
  private LocalDateTime eventBeginDate;

  @Schema(description = "이벤트종료일시")
  private LocalDateTime eventEndDate;

  @Schema(description = "이벤트구분코드ID")
  private String eventSectionCodeId;

  @Schema(description = "추첨방식코드ID")
  private String drwtMethodCodeId;

  @Schema(description = "참여여부")
  private String participationAt;

  @Builder.Default
  private List<PointPaymentResponse> pointPayment = new ArrayList<>();
}
