package com.playground.api.sample.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "SampleResponse", description = "샘플 조회 요청 데이터")
@Getter
public class SmpleRequest extends BaseDto {
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
