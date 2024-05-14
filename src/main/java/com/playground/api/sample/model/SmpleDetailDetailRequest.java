package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "SmpleDetailDetailRequest", description = "샘플 상세 상세 조회 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class SmpleDetailDetailRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 샘플 일련번호
   */
  @Schema(description = "샘플 일련번호", example = "1234567890")
  private Integer sampleSsno;

  /**
   * 샘플 상세 일련번호
   */
  @Schema(description = "샘플 상세 일련번호", example = "1234567890")
  private Integer sampleDetailSsno;

  /**
   * 샘플 상세 상세 일련번호
   */
  @Schema(description = "샘플 상세 상세 일련번호", example = "1234567890")
  private Integer sampleDetailDetailSsno;

  /**
   * 샘플 상세 상세 첫번째 내용
   */
  @Schema(description = "샘플 상세 상세 첫번째 내용", example = "test1")
  private String sampleDetailDetailContent1;

  /**
   * 샘플 상세 상세 두번째 내용
   */
  @Schema(description = "샘플 상세 상세 두번째 내용", example = "test2")
  private String sampleDetailDetailContent2;

  /**
   * 샘플 상세 상세 세번째 내용
   */
  @Schema(description = "샘플 상세 상세 세번째 내용", example = "test3")
  private String sampleDetailDetailContent3;
}
