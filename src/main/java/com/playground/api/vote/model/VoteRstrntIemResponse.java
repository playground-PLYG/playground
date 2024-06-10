package com.playground.api.vote.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "VoteRstrntIemResponse", description = "식당메뉴정보 리스트")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteRstrntIemResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 항목일련번호
   */
  @Schema(description = "항목일련번호", example = "1234567890")
  private Integer iemSsno;

  /**
   * 항목명
   */
  @Schema(description = "항목명", example = "갓덴스시")
  private String iemName;

}
