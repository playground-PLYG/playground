package com.playground.api.eventUser.model;

import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "EventListResponse", description = "이벤트 목록 응답")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventUserListResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  @Schema(description = "이벤트일련번호")
  private Integer eventSerial;

  @Schema(description = "이벤트명")
  private String eventName;

  @Schema(description = "이벤트시작일시")
  private LocalDateTime eventBeginDate;

  @Schema(description = "이벤트종료일시")
  private LocalDateTime eventEndDate;

  @Schema(description = "이벤트썸네일파일일련번호")
  private Integer eventThumbFileSn;

  @Schema(description = "참여여부")
  private String participationAt;

}
