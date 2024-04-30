package com.playground.api.hashtag.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "HashtagRequest", description = "hashtag 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class HashtagRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "해시태그일련번호")
  private Integer hashtagNo;

  @Schema(description = "해시태그명")
  private String hashtagData;

}
