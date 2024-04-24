package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(name = "SampleResponse", description = "샘플 조회 응답 데이터")
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
}
