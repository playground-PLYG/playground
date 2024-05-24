package com.playground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import com.playground.api.message.component.DiscordComponent;

@Configuration
public class RestClientConfig {

  @Bean
  public DiscordComponent discordWebhook() {
    RestClient restClient = RestClient.builder().baseUrl("https://discord.com/api").build();
    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(DiscordComponent.class);
  }
}
