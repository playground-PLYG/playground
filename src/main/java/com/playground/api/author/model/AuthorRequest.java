package com.playground.api.author.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "AuthorRequest", description = "author 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class AuthorRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "권한ID")
  private String authorId;

  @Schema(description = "권한명")
  private String authorNm;

  @Schema(description = "삭제여부")
  private String deleteAt;

}
