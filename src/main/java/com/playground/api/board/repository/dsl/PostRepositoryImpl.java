package com.playground.api.board.repository.dsl;

import static com.playground.api.board.entity.QPostEntity.postEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.board.entity.PostEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<PostEntity> getPostList(PostEntity request) {
    return queryFactory.selectFrom(postEntity)
        .where(postEntity.noticeEntity.bbsId.eq(request.getNoticeEntity().getBbsId()), nttSjLkie(request.getNttSj())).orderBy(postEntity.nttSn.asc())
        .fetch();

  }

  /* 동적쿼리를 위한 함수 */
  private BooleanExpression nttSjLkie(String nttSj) {
    if (nttSj == null) {
      return null;
    }

    return postEntity.nttSj.like(nttSj + "%");
  }

}
