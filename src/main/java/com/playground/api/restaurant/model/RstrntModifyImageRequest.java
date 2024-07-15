package com.playground.api.restaurant.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "RstrntModifyImageRequest", description = "식당 이미지 수정 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RstrntModifyImageRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "식당일련번호는 필수 값 입니다.")
  @Schema(description = "식당일련번호", requiredMode = RequiredMode.REQUIRED)
  private Integer restaurantSerialNo;

  @NotNull(message = "기존이미지파일은 필수 값 입니다.")
  @Schema(description = "기존이미지파일ID", requiredMode = RequiredMode.REQUIRED)
  private Integer oldImageFileId;

  @NotNull(message = "이미지파일은 필수 값 입니다.")
  @Schema(description = "이미지파일ID", requiredMode = RequiredMode.REQUIRED)
  private Integer imageFileId;

  // @NotNull(message = "이미지파일는 필수 값 입니다.")
  // @Schema(description = "이미지파일ID 리스트", requiredMode = RequiredMode.REQUIRED)
  // private List<Integer> imageFileIds;
}
