package com.playground.api.vote.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "VoteQestnAddRequest", description = "질문등록 필요한 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class VoteQestnAddRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;


  /**
   * 투표일련번호
   */
  @Schema(description = "투표일련번호", example = "1234567890")
  private Integer voteSsno;

  /**
   * 투표종류코드
   */
  @Schema(description = "투표종류코드")
  private String voteKndCd;


  /**
   * 복수선택여부
   */
  @Schema(description = "복수선택여부", example = "Y")
  private String compoundNumberChoiceAlternative;

  /**
   * 익명투표여부
   */
  @Schema(description = "익명투표여부")
  private String anonymityVoteAlternative;


  /**
   * 질문내용
   */
  @Schema(description = "질문내용", example = "회식 가능한 날짜를 선택해주세요")
  private String questionContents;


  /**
   * 투표항목 객체
   */
  @Schema(description = "항목객체", example = "voteIemRequest")
  private List<VoteQestnIemAddRequest> voteIemRequestList;
}
