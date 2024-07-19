package com.playground.api.vote.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "VoteAddRequest", description = "투표 등록/수정 요청에 필요한 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class VoteAddRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 사용자ID
   */
  @Schema(description = "사용자ID", example = "sungjong")
  private String answerUserId;

  /**
   * 투표제목
   */
  @Schema(description = "투표제목", example = "오점뭐")
  private String voteSubject;

  /**
   * 투표시작일시
   */
  @Schema(description = "투표시작일시", example = "yyyy-MM-dd HH:mm")
  private String voteBeginDate;

  /**
   * 투표종료일시
   */
  @Schema(description = "투표종료일시", example = "yyyy-MM-dd HH:mm")
  private String voteEndDate;


  /**
   * 투표노출여부
   */
  @Schema(description = "투표노출여부", example = "Y")
  private String voteExposureAlternative;


  /**
   * 투표전송코드
   */
  @Schema(description = "투표전송코드", example = "NOW")
  private String voteTransmissionCode;


  /**
   * 질문들
   */
  @Schema(description = "질문객체", example = "voteQestnRequestList")
  private List<VoteQestnAddRequest> voteQestnRequestList;
}
