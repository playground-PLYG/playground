package com.playground.api.author.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "MberAuthorRequest", description = "mberAuthor 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class MberAuthorRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "회원ID")
  private String mberId;

  @Schema(description = "권한ID")
  private String authorId;

}
