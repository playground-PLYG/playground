package com.playground.api.event.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "EventResponse", description = "게시판 CRUD")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트일련번호")
  private Integer eventSerial;

  @Schema(description = "이벤트명")
  private String eventName;

  @Schema(description = "이벤트시작일시")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime eventBeginDate;

  @Schema(description = "이벤트종료일시")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime eventEndDate;

  @Schema(description = "진행상태")
  private String progrsSttus;

  @Schema(description = "이벤트썸네일파일일련번호")
  private Integer eventThumbFileSn;

  @Schema(description = "당첨자수")
  private Integer przwnerCount;

  @Schema(description = "이벤트구분코드ID")
  private String eventSectionCodeId;

  @Schema(description = "추첨방식코드ID")
  private String drwtMethodCodeId;

  @Schema(description = "포인트지급방식코드ID")
  private String pointPymntMethodCodeId;

  @Schema(description = "총포인트값")
  private Integer totalPointValue;

  @Schema(description = "컨테츠내용")
  private String cntntsContents;

  @Schema(description = "노출여부")
  private String expsrAt;

  @Schema(description = "추첨일시")
  private LocalDateTime drwtDate;

  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "등록일시")
  private LocalDateTime registDt;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;

  @Schema(description = "수정일시")
  private LocalDateTime updtDt;

  @Builder.Default
  private List<PointPaymentResponse> pointPayment = new ArrayList<>();
}
