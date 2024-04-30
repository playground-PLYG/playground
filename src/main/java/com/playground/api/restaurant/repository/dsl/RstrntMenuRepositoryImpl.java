package com.playground.api.restaurant.repository.dsl;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.playground.api.restaurant.entity.QRstrntMenuEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RstrntMenuRepositoryImpl implements RstrntMenuRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QRstrntMenuEntity tbRstrntMenu = QRstrntMenuEntity.rstrntMenuEntity;

  @Override
  public List<RstrntMenuEntity> findAllList(RstrntMenuEntity entity) {
    return queryFactory.selectFrom(tbRstrntMenu)
        .where(tbRstrntMenu.rstrntSn.eq(entity.getRstrntSn()), rstrntMenuNmLike(entity.getRstrntMenuNm()), rstrntMenuPcEq(entity.getRstrntMenuPc()))
        .fetch();
  }

  private BooleanExpression rstrntMenuNmLike(String rstrntMenuNm) {
    return StringUtils.isNotBlank(rstrntMenuNm) ? tbRstrntMenu.rstrntMenuNm.like(rstrntMenuNm) : null;
  }

  private BooleanExpression rstrntMenuPcEq(BigDecimal rstrntMenuPc) {
    return rstrntMenuPc != null ? tbRstrntMenu.rstrntMenuPc.eq(rstrntMenuPc) : null;
  }
}
