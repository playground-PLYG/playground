package com.playground.api.menu.repository.dsl;

import static com.playground.api.author.entity.QAuthorMenuEntity.authorMenuEntity;
import static com.playground.api.author.entity.QMberAuthorEntity.mberAuthorEntity;
import static com.playground.api.member.entity.QMberEntity.mberEntity;
import static com.playground.api.menu.entity.QMenuEntity.menuEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.entity.QMenuEntity;
import com.playground.api.menu.model.MenuResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MenuRepositoryImpl implements MenuRepositoryCustom {

  private final JPAQueryFactory queryFactory;


  @Override
  public List<MenuResponse> getUpperMenuList(String mberId) {
    QMenuEntity menuEntity2 = new QMenuEntity("menuEntity2");
    return queryFactory
        .select(Projections.fields(MenuResponse.class, menuEntity.menuSn, menuEntity.menuNm, menuEntity.menuUrl, menuEntity.menuDepth,
            menuEntity.menuSortOrdr, menuEntity.upperMenuSn, menuEntity.useAt,
            new CaseBuilder()
                .when(JPAExpressions.select(menuEntity2.count()).from(menuEntity2).where(menuEntity.menuSn.eq(menuEntity2.upperMenuSn)).gt((long) 0))
                .then("Y").otherwise("N").as("lwprtMenuHoldAt")))
        .distinct().from(mberEntity).rightJoin(mberAuthorEntity).on(mberEntity.mberId.eq(mberAuthorEntity.mberId)).rightJoin(authorMenuEntity)
        .on(mberAuthorEntity.authorId.eq(authorMenuEntity.authorId)).rightJoin(menuEntity).on(authorMenuEntity.menuSn.eq(menuEntity.menuSn))
        .where(mberIdEq(mberId).or(authorMenuEntity.authorId.eq("ROLE_DEFAULT")), menuEntity.useAt.eq("Y"), menuEntity.upperMenuSn.isNull()).fetch();
  }

  @Override
  public List<MenuResponse> getLowerMenuList(String mberId) {
    return queryFactory
        .select(Projections.fields(MenuResponse.class, menuEntity.menuSn, menuEntity.menuNm, menuEntity.menuUrl, menuEntity.menuDepth,
            menuEntity.menuSortOrdr, menuEntity.upperMenuSn, menuEntity.useAt))
        .distinct().from(mberEntity).rightJoin(mberAuthorEntity).on(mberEntity.mberId.eq(mberAuthorEntity.mberId)).rightJoin(authorMenuEntity)
        .on(mberAuthorEntity.authorId.eq(authorMenuEntity.authorId)).rightJoin(menuEntity).on(authorMenuEntity.menuSn.eq(menuEntity.menuSn))
        .where(mberIdEq(mberId).or(authorMenuEntity.authorId.eq("ROLE_DEFAULT")), menuEntity.useAt.eq("Y"), menuEntity.upperMenuSn.isNotNull())
        .fetch();
  }


  @Override
  public Page<MenuEntity> getMenuPageList(String menuNm, String menuUrl, String useAt, Pageable pageable) {

    List<MenuEntity> content = queryFactory.selectFrom(menuEntity).where(menuNmLike(menuNm), menuUrlLike(menuUrl), useAtEq(useAt))
        .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery =
        queryFactory.select(menuEntity.count()).from(menuEntity).where(menuNmLike(menuNm), menuUrlLike(menuUrl), useAtEq(useAt));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }


  /* 동적쿼리를 위한 함수 */
  private BooleanExpression mberIdEq(String mberId) {
    if (mberId == null) {
      return null;
    }

    return mberEntity.mberId.eq(mberId);
  }


  private BooleanExpression menuNmLike(String menuNm) {
    if (menuNm == null) {
      return null;
    }

    return menuEntity.menuNm.like("%" + menuNm + "%");
  }

  private BooleanExpression menuUrlLike(String menuUrl) {
    if (menuUrl == null) {
      return null;
    }

    return menuEntity.menuUrl.like("%" + menuUrl + "%");
  }


  private BooleanExpression useAtEq(String useAt) {
    if (ObjectUtils.isEmpty(useAt)) {
      return null;
    } else {
      return menuEntity.useAt.eq(useAt);
    }
  }

}
