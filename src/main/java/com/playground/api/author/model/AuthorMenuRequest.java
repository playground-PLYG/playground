package com.playground.api.author.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "AuthorMenuRequest", description = "AuthorMenu 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class AuthorMenuRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "권한ID")
  private String authorId;

  @Schema(description = "메뉴일련번호")
  private Integer menuSn;

}
