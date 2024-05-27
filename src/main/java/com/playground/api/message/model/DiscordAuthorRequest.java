package com.playground.api.message.model;

import java.io.Serial;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscordAuthorRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String name;

  private String url; // http(s)로 시작하는 경로 필수

  @JsonProperty("icon_url")
  private String iconUrl; // http(s)로 시작하는 경로 필수

}
