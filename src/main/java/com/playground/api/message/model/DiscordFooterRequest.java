package com.playground.api.message.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class DiscordFooterRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String text;

  @JsonProperty("icon_url")
  private String iconUrl;
}
