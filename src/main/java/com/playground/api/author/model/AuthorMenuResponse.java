package com.playground.api.author.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "AuthorMenuResponse", description = "AuthorMenu 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class AuthorMenuResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "권한ID")
  private String authorId;

  @Schema(description = "권한명")
  private String authorNm;

  @Schema(description = "메뉴일련번호")
  private Integer menuSn;

  @Schema(description = "메뉴명")
  private String menuNm;

  @Schema(description = "메뉴URL")
  private String menuUrl;

  @Schema(description = "권한별 메뉴 등록 여부")
  private String authorMenuAddAt;

}
