package com.playground.api.event.model;

import java.time.LocalDateTime;
import com.playground.annotation.ExcelDown;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "EventResultExcelResponse", description = "이벤트 결과 엑셀다운로드")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventResultExcelResponse extends BaseDto {
  private static final long serialVersionUID = 1L;

  // @Schema(description = "이벤트일련번호")
  // private Integer eventSerial;

  @Schema(description = "이벤트명")
  private String eventName;

  @Schema(description = "이름")
  @ExcelDown(headerName = "이름", order = 1)
  private String memberName;

  @Schema(description = "아이디")
  @ExcelDown(headerName = "아이디", order = 2)
  private String memberId;

  @Schema(description = "포인트")
  @ExcelDown(headerName = "포인트", order = 3)
  private Integer przwinPointVal;

  @Schema(description = "연락처")
  @ExcelDown(headerName = "연락처", order = 4)
  private String memberTelno;

  @Schema(description = "참여일시")
  @ExcelDown(headerName = "참여일시", order = 5)
  private String formatEventPartcptnDate;

  private LocalDateTime eventPartcptnDate;

}
