package com.playground.api.menu.repository.dsl;

import static com.playground.api.author.entity.QAuthorMenuEntity.authorMenuEntity;
import static com.playground.api.author.entity.QMberAuthorEntity.mberAuthorEntity;
import static com.playground.api.member.entity.QMberEntity.mberEntity;
import static com.playground.api.menu.entity.QMenuEntity.menuEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.playground.api.menu.model.MenuResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MenuRepositoryImpl implements MenuRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<MenuResponse> getMenuList(String mberId) {
    return queryFactory
        .select(Projections.fields(MenuResponse.class, menuEntity.menuSn, menuEntity.menuNm, menuEntity.menuUrl, menuEntity.menuDepth,
            menuEntity.menuSortOrdr, menuEntity.upperMenuSn, menuEntity.useAt))
        .distinct().from(mberEntity).rightJoin(mberAuthorEntity).on(mberEntity.mberId.eq(mberAuthorEntity.mberId)).rightJoin(authorMenuEntity)
        .on(mberAuthorEntity.authorId.eq(authorMenuEntity.authorId)).rightJoin(menuEntity).on(authorMenuEntity.menuSn.eq(menuEntity.menuSn))
        .where(mberIdEq(mberId).or(authorMenuEntity.authorId.eq("ROLE_DEFAULT")), menuEntity.useAt.eq("Y")).fetch();
  }

  public List<MenuResponse> getSelectByCondition(String menuNm, String menuUrl, String useAt) {
    return queryFactory.select(Projections.fields(MenuResponse.class, menuEntity.menuSn, menuEntity.menuNm, menuEntity.menuUrl, menuEntity.menuDepth,
        menuEntity.menuSortOrdr, menuEntity.upperMenuSn, menuEntity.useAt, menuEntity.registUsrId, menuEntity.registDt, menuEntity.updtUsrId,
        menuEntity.updtDt)).from(menuEntity).where(menuNmLike(menuNm)).fetch();
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
