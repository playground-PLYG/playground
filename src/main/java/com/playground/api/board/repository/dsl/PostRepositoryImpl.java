package com.playground.api.board.repository.dsl;

import static com.playground.api.board.entity.QPostEntity.postEntity;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.board.entity.PostEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<PostEntity> getPostList(PostEntity request, Pageable pageable) {


    List<PostEntity> post = queryFactory.selectFrom(postEntity)
        .where(postEntity.noticeEntity.bbsId.eq(request.getNoticeEntity().getBbsId()), nttSjLkie(request.getNttSj())).orderBy(postEntity.nttSn.desc())
        .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery = queryFactory.select(postEntity.count()).from(postEntity)
        .where(postEntity.noticeEntity.bbsId.eq(request.getNoticeEntity().getBbsId()), nttSjLkie(request.getNttSj()));

    return PageableExecutionUtils.getPage(post, pageable, countQuery::fetchOne);
  }

  /* 동적쿼리를 위한 함수 */
  private BooleanExpression nttSjLkie(String nttSj) {

    return StringUtils.isNotBlank(nttSj) ? postEntity.nttSj.like(nttSj + "%") : null;
  }

}

