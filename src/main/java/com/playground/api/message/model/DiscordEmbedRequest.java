package com.playground.api.message.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class DiscordEmbedRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String title;

  private String description;

  private String url;

  private DiscordFooterRequest footer;

  private DiscordThumbnailRequest thumbnail;

  private DiscordImageRequest image;

  private DiscordAuthorRequest author;

  @Default
  private List<DiscordFieldRequest> fields = new ArrayList<>();

  public DiscordEmbedRequest setFooter(String text, String icon) {
    this.footer = new DiscordFooterRequest(text, icon);
    return this;
  }

  public DiscordEmbedRequest setThumbnail(String url) {
    this.thumbnail = new DiscordThumbnailRequest(url);
    return this;
  }

  public DiscordEmbedRequest setImage(String url) {
    this.image = new DiscordImageRequest(url);
    return this;
  }

  public DiscordEmbedRequest setAuthor(String name, String url, String icon) {
    this.author = new DiscordAuthorRequest(name, url, icon);
    return this;
  }

  public DiscordEmbedRequest addField(String name, String value, boolean inline) {
    this.fields.add(new DiscordFieldRequest(name, value, inline));
    return this;
  }

}
