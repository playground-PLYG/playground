package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class JoinResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 샘플일련번호
   */
  private Integer smpleSn;

  /**
   * 샘플첫번째내용
   */
  private String smpleFirstCn;

  /**
   * 샘플두번째내용
   */
  private String smpleSeconCn;

  /**
   * 샘플세번째내용
   */
  private String smpleThrdCn;

  /**
   * 샘플상세일련번호
   */
  private Integer smpleDetailSn;

  /**
   * 샘플상세첫번째내용
   */
  private String smpleDetailFirstCn;

  /**
   * 샘플상세두번째내용
   */
  private String smpleDetailSeconCn;

  /**
   * 샘플상세세번째내용
   */
  private String smpleDetailThrdCn;
}
