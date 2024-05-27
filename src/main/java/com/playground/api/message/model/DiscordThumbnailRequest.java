package com.playground.api.message.model;

import java.io.Serial;
import java.io.Serializable;
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
public class DiscordThumbnailRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  
  private String url;

}
