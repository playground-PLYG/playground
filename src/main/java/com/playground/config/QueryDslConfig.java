package com.playground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  JPAQueryFactory jpaQueryFactory() {
    // Spring boot 3.x 버전 부터 JPQLTemplates.DEFAULT 를 넣어줘야 queryDsl에서 transForm 구문이 사용 가능
    return new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
  }

  @Bean
  com.querydsl.sql.Configuration querydslConfiguration() {
    SQLTemplates templates = PostgreSQLTemplates.builder().build();
    // change to your Templates
    com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
    SpringExceptionTranslator springExceptionTranslator = new SpringExceptionTranslator();
    configuration.setExceptionTranslator(springExceptionTranslator);
    return configuration;
  }
}
