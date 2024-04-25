package com.playground.api.sample.model;

import java.io.Serial;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.playground.constants.WebSocketMessageType;
import com.playground.constants.WebSocketTargetType;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "WebSocketDto", description = "WebSocket 통신 데이터")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class WebSocketDto extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonInclude(Include.NON_NULL)
  private WebSocketTargetType targetType;

  private WebSocketMessageType messageType;

  private LocalDateTime sendDate;

  private String senderId;

  @JsonInclude(Include.NON_NULL)
  private String receiverId;

  private String message;

}
