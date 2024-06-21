package com.playground.api.member.repository.dsl;

import static com.playground.api.author.entity.QAuthorEntity.authorEntity;
import static com.playground.api.author.entity.QMberAuthorEntity.mberAuthorEntity;
import static com.playground.api.member.entity.QMberEntity.mberEntity;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import com.playground.api.member.entity.MberEntity;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberModifyInfoRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MberRepositoryImpl implements MberRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public MberInfoResponse findByIdDetail(String userId) {
    return queryFactory
        .select(Projections.fields(MberInfoResponse.class, mberEntity.mberId, mberEntity.ciCn, mberEntity.diCn, mberEntity.mberBymd,
            mberEntity.mberEmailAdres, mberEntity.mberNm, mberEntity.mberSexdstnCode, mberEntity.mberTelno,
            Expressions.stringTemplate("string_agg({0}, ',')", authorEntity.authorNm).as("authorNm"),
            Expressions.stringTemplate("string_agg({0}, ',')", authorEntity.authorId).as("authorId")))
        .from(mberEntity).leftJoin(mberAuthorEntity).on(mberEntity.mberId.eq(mberAuthorEntity.mberId)).leftJoin(authorEntity)
        .on(mberAuthorEntity.authorId.eq(authorEntity.authorId)).where(mberEntity.mberId.eq(userId)).groupBy(mberEntity.mberId).fetchOne();
  }

  @Override
  public Page<MberEntity> getMberPageList(String mberId, String mberNm, Pageable pageable) {

    List<MberEntity> content = queryFactory.selectFrom(mberEntity).where(mberIdLike(mberId), mberNmLike(mberNm)).offset(pageable.getOffset())
        .limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery = queryFactory.select(mberEntity.count()).from(mberEntity).where(mberIdLike(mberId), mberNmLike(mberNm));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private BooleanExpression mberNmLike(String mberNm) {
    return StringUtils.isNotBlank(mberNm) ? mberEntity.mberNm.startsWith(mberNm) : null;
  }

  private BooleanExpression mberIdLike(String mberId) {
    return StringUtils.isNotBlank(mberId) ? mberEntity.mberId.startsWith(mberId) : null;
  }

  @Override
  public void updateMberinfoByMberId(MberModifyInfoRequest req) {
    queryFactory.update(mberEntity).set(mberEntity.mberNm, req.getMberNm()).set(mberEntity.mberBymd, req.getMberBymd())
        .set(mberEntity.mberSexdstnCode, req.getMberSexdstnCode()).set(mberEntity.mberTelno, req.getMberTelno())
        .where(mberEntity.mberId.eq(req.getMberId())).execute();

  }

}
