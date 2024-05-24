package com.playground.api.message.component;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import com.playground.api.message.model.DiscordRequest;

@Component
@HttpExchange
public interface DiscordComponent {

  @PostExchange(url = "/webhooks/{key}")
  void sendDiscord(@PathVariable String key, @RequestBody DiscordRequest req);
}
