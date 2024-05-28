package com.playground.api.author.repository.dsl;

import static com.playground.api.author.entity.QAuthorEntity.authorEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.playground.api.author.model.AuthorResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<AuthorResponse> getAuthorList(String authorId, String authorNm, String deleteAt) {
    return queryFactory
        .select(Projections.fields(AuthorResponse.class, authorEntity.authorId, authorEntity.authorNm, authorEntity.deleteAt,
            authorEntity.registUsrId, authorEntity.registDt, authorEntity.updtUsrId, authorEntity.updtDt))
        .from(authorEntity).where(authorIdLike(authorId), authorNmLike(authorNm), deleteAtEq(deleteAt)).orderBy(authorEntity.authorId.asc()).fetch();
  }

  /* 동적쿼리를 위한 함수 */
  private BooleanExpression authorIdLike(String authorId) {
    if (authorId == null) {
      return null;
    }

    return authorEntity.authorId.like("%" + authorId + "%");
  }

  private BooleanExpression authorNmLike(String authorNm) {
    if (authorNm == null) {
      return null;
    }

    return authorEntity.authorNm.like("%" + authorNm + "%");
  }

  private BooleanExpression deleteAtEq(String deleteAt) {
    if (ObjectUtils.isEmpty(deleteAt)) {
      return null;
    } else {
      return authorEntity.deleteAt.eq(deleteAt);
    }
  }

}
