package com.playground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    //Spring boot 3.x 버전 부터 JPQLTemplates.DEFAULT 를 넣어줘야 queryDsl에서 transForm 구문이 사용 가능
    return new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
  }
}
