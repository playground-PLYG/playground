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

@Schema(name = "VoteIemResponse", description = "투표항목조회 및 등록,수정 응답에 필요한 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteQestnIemResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 항목ID
   */
  @Schema(description = "항목ID", example = "0000123456")
  private Integer itemSsno;

  /**
   * 항목명
   */
  @Schema(description = "항목명", example = "또성골뱅이")
  private String itemName;

  /**
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "1234567890")
  private Integer questionSno;

  /**
   * 투표일련번호
   */
  @Schema(description = "투표일련번호", example = "1234567890")
  private Integer voteSno;

  /**
   * 항목식별ID
   */
  @Schema(description = "항목식별ID", example = "1234567890")
  private String itemIdentificationId;
}
