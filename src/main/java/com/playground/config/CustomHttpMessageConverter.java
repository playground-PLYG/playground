package com.playground.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomHttpMessageConverter extends MappingJackson2HttpMessageConverter {
  public CustomHttpMessageConverter(ObjectMapper objectMapper) {
    super(objectMapper);
    objectMapper.registerModule(new JavaTimeModule());
    setObjectMapper(objectMapper);
  }

  @Override
  public boolean canWrite(Class<?> clazz, MediaType mediaType) {
    return canWrite(mediaType);
  }
}
