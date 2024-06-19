package com.playground.api.code.repository.dsl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import com.playground.api.code.entity.CodeEntity;
import com.playground.api.code.entity.QCodeEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QCodeEntity tbCode = QCodeEntity.codeEntity;

  @Override
  public List<CodeEntity> findAll(CodeEntity entity) {
    return queryFactory.selectFrom(tbCode).where(codeIdLike(entity.getCodeId()), codeNmLike(entity.getCodeNm()),
        upperCodeIdLike(entity.getUpperCodeId()), groupCodeAtEq(entity.getGroupCodeAt())).fetch();
  }

  private BooleanExpression codeIdLike(String codeId) {
    return StringUtils.isNotBlank(codeId) ? tbCode.codeId.startsWith(codeId) : null;
  }

  private BooleanExpression codeNmLike(String codeNm) {
    return StringUtils.isNotBlank(codeNm) ? tbCode.codeNm.startsWith(codeNm) : null;
  }

  private BooleanExpression upperCodeIdLike(String upperCodeId) {
    return StringUtils.isNotBlank(upperCodeId) ? tbCode.upperCodeId.startsWith(upperCodeId) : null;
  }

  private BooleanExpression groupCodeAtEq(String groupCodeAt) {
    return StringUtils.isNotBlank(groupCodeAt) ? tbCode.groupCodeAt.eq(groupCodeAt) : null;
  }

  @Override
  public Page<CodeEntity> getCodePageList(CodeEntity entity, Pageable pageable) {

    List<CodeEntity> content =
        queryFactory.selectFrom(tbCode)
            .where(codeIdLike(entity.getCodeId()), codeNmLike(entity.getCodeNm()), upperCodeIdLike(entity.getUpperCodeId()),
                groupCodeAtEq(entity.getGroupCodeAt()))
            .orderBy(tbCode.codeSn.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery = queryFactory.select(tbCode.count()).from(tbCode).where(codeIdLike(entity.getCodeId()), codeNmLike(entity.getCodeNm()),
        upperCodeIdLike(entity.getUpperCodeId()), groupCodeAtEq(entity.getGroupCodeAt()));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }



}
