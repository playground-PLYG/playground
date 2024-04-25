package com.playground.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final ObjectMapper objectMapper;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods(HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(),
        HttpMethod.OPTIONS.name());
  }

  /*
   *
   *
   * TODO spring boot 3.1.X 버전 업 하면서 lucy-xss-servlet-filter 내부의 javax 패키지 사용하는 부분이 있어서 임시 주석** //NOSONAR
   *
   * @Bean public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() { FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>(); //NOSONAR
   *
   * filterRegistration.setFilter(new XssEscapeServletFilter()); filterRegistration.setOrder(1); filterRegistration.addUrlPatterns("/*"); //NOSONAR
   *
   * return filterRegistration; } //NOSONAR
   */

  @Bean
  public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
    ObjectMapper copy = objectMapper.copy();

    copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());

    return new MappingJackson2HttpMessageConverter(copy);
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper copy = objectMapper.copy();

    copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());

    converters.add(1, new CustomHttpMessageConverter(copy));
  }
}
