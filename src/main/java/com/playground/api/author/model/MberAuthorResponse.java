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

@Schema(name = "MberAuthorResponse", description = "mberAuthor 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MberAuthorResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "회원ID")
  private String mberId;

  @Schema(description = "권한ID")
  private String authorId;

  @Schema(description = "권한명")
  private String authorNm;

  @Schema(description = "회원별 권한 등록 여부")
  private String mberAuthorAddAt;

}
