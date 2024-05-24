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
public class DiscordRequest implements Serializable {

  /*
   * https://discord.com/developers/docs/resources/webhook 참고
   * 데이터 세팅중 하나라도 잘못되어 있으면 전송 안됨
   * ex) Author에 url이 http(s)로 시작안하는 경우
   */
  
  @Serial
  private static final long serialVersionUID = 1L;

  private String content;
  
  private String username;
  
  @JsonProperty("avatar_url")
  private String avatarUrl;
  
  private boolean tts;
  
  public List<Embed> embeds = new ArrayList<>();

  public void addEmbed(Embed embed) {
    this.embeds.add(embed);
  }

  @Getter
  @Setter
  public static class Embed {
    private String title;

    private String description;

    private String url;

    private Footer footer;

    private Thumbnail thumbnail;

    private Image image;

    private Author author;

    private List<Field> fields = new ArrayList<>();

    public Embed setFooter(String text, String icon) {
      this.footer = new Footer(text, icon);
      return this;
    }

    public Embed setThumbnail(String url) {
      this.thumbnail = new Thumbnail(url);
      return this;
    }

    public Embed setImage(String url) {
      this.image = new Image(url);
      return this;
    }

    public Embed setAuthor(String name, String url, String icon) {
      this.author = new Author(name, url, icon);
      return this;
    }

    public Embed addField(String name, String value, boolean inline) {
      this.fields.add(new Field(name, value, inline));
      return this;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Footer {
      private String text;
      
      @JsonProperty("icon_url")
      private String iconUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Thumbnail {
      private String url;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Image {
      private String url;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Author {
      private String name;
      
      private String url; // http(s)로 시작하는 경로 필수
      
      @JsonProperty("icon_url")
      private String iconUrl; // http(s)로 시작하는 경로 필수
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Field {
      private String name;
      
      private String value;
      
      private boolean inline;
    }
  }
}
