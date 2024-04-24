package com.playground.api.sample.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Schema(name = "SmpleDetailDetailResponse", description = "샘플 상세 상세 조회 응답 데이터")
@Getter
@Setter
public class SmpleDetailDetailResponse extends BaseDto {
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
   * 샘플 상세 첫번째 내용
   */
  @Schema(description = "샘플 상세 첫번째 내용", example = "test1")
  private String sampleDetailDetailContent1;

  /**
   * 샘플 상세 두번째 내용
   */
  @Schema(description = "샘플 상세 두번째 내용", example = "test2")
  private String sampleDetailDetailContent2;

  /**
   * 샘플 상세 세번째 내용
   */
  @Schema(description = "샘플 상세 세번째 내용", example = "test3")
  private String sampleDetailDetailContent3;
}
