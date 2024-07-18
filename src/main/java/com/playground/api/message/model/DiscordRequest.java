package com.playground.api.message.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscordRequest implements Serializable {

  /*
   * https://discord.com/developers/docs/resources/webhook 참고 데이터 세팅중 하나라도 잘못되어 있으면 전송 안됨 ex) Author에 url이 http(s)로 시작안하는 경우
   */

  @Serial
  private static final long serialVersionUID = 1L;

  private String apiNm;

  private String content;

  private String username;

  private String voteTransmissionCode;

  @JsonProperty("avatar_url")
  private String avatarUrl;

  private boolean tts;

  @Default
  public List<DiscordEmbedRequest> embeds = new ArrayList<>();

  public void addEmbed(DiscordEmbedRequest embed) {
    this.embeds.add(embed);
  }

}
