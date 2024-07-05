package com.playground.api.vote.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "StatisticsDetailDetailResponse", description = "사용자 답변들을 통계 내기 위한 Detail Detail response")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class StatisticsDetailDetailResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 항목ID
   */
  @Schema(description = "항목ID", example = "00001456")
  private Integer itemSsno;

  /**
   * 항목이름
   */
  @Schema(description = "항목이름", example = "김찌")
  private String itemName;

  /**
   * 해당 항목 득표수
   */
  @Schema(description = "특표수", example = "14")
  private Long itemCount;

  /**
   * 해당 항목 투표한 유저
   */
  @Schema(description = "투표한 유저들", example = "[test1, test2, test3]")
  private List<String> selUserIdList;

}
