package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class SseDto extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  private String id;

  private Long sendDate;

  private transient Object data;

}
