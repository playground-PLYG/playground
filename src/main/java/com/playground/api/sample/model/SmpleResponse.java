package com.playground.api.sample.model;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "SampleResponse", description = "샘플 조회 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class SmpleResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 샘플 일련번호
   */
  @Schema(description = "샘플 일련번호", example = "1234567890")
  private Integer sampleSsno;

  /**
   * 샘플 첫번째 내용
   */
  @Schema(description = "샘플 첫번째 내용", example = "test1")
  private String sampleContent1;

  /**
   * 샘플 두번째 내용
   */
  @Schema(description = "샘플 두번째 내용", example = "test2")
  private String sampleContent2;

  /**
   * 샘플 세번째 내용
   */
  @Schema(description = "샘플 세번째 내용", example = "test3")
  private String sampleContent3;
  
  /**
   * 시간
   */
  @Schema(description = "시간", example = "yyyy-mm-dd hh:mm:ss.SSS")
  private LocalDateTime useDate;
  
  /**
   * 시간
   */
  @Schema(description = "시작시간", example = "yyyy-mm-dd hh:mm:ss.SSS")
  private LocalDateTime beginDate;
  
  /**
   * 시간
   */
  @Schema(description = "종료시간", example = "yyyy-mm-dd hh:mm:ss.SSS")
  private LocalDateTime endDate;
  
  @Default
  @Schema(description = "종료시간", example = "yyyy-mm-dd hh:mm:ss.SSS")
  private LocalDate localDate = LocalDate.now();
  
  @Default
  @Schema(description = "종료시간", example = "yyyy-mm-dd hh:mm:ss.SSS")
  private LocalTime localTime = LocalTime.now();
  
  /**
   * detail List
   */
  private final List<SmpleDetailResponse> smpleDetailResponseList = new ArrayList<>();
}
