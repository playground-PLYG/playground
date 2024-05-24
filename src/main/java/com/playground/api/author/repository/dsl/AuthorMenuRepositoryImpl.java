package com.playground.api.author.repository.dsl;

import static com.playground.api.author.entity.QAuthorMenuEntity.authorMenuEntity;
import static com.playground.api.menu.entity.QMenuEntity.menuEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.author.model.AuthorMenuResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorMenuRepositoryImpl implements AuthorMenuRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<AuthorMenuResponse> getAuthorMenuList(String authorId) {
    return queryFactory.select(Projections.fields(AuthorMenuResponse.class, Expressions.asString(authorId).as("authorId"), menuEntity.menuSn,
        menuEntity.menuNm, menuEntity.menuUrl,
        new CaseBuilder()
            .when(JPAExpressions.select(authorMenuEntity.count()).from(authorMenuEntity)
                .where(menuEntity.menuSn.eq(authorMenuEntity.menuSn), authorMenuEntity.authorId.eq(authorId)).gt((long) 0))
            .then("Y").otherwise("N").as("authorMenuAddAt")))
        .from(menuEntity).where(menuEntity.useAt.eq("Y")).orderBy(menuEntity.menuSn.asc()).fetch();
  }

}
