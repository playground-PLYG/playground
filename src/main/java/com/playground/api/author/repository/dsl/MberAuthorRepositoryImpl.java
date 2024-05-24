package com.playground.api.author.repository.dsl;

import static com.playground.api.author.entity.QAuthorEntity.authorEntity;
import static com.playground.api.author.entity.QMberAuthorEntity.mberAuthorEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.author.model.MberAuthorResponse;
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
public class MberAuthorRepositoryImpl implements MberAuthorRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<MberAuthorResponse> getMberAuthorList(String mberId) {
    return queryFactory
        .select(Projections.fields(MberAuthorResponse.class, Expressions.asString(mberId).as("mberId"), authorEntity.authorId, authorEntity.authorNm,
            new CaseBuilder()
                .when(JPAExpressions.select(mberAuthorEntity.count()).from(mberAuthorEntity)
                    .where(authorEntity.authorId.eq(mberAuthorEntity.authorId), mberAuthorEntity.mberId.eq(mberId)).gt((long) 0))
                .then("Y").otherwise("N").as("mberAuthorAddAt")))
        .from(authorEntity).orderBy(authorEntity.authorId.asc()).fetch();
  }

}
