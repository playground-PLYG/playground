package com.playground.api.restaurant.repository.dsl;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.playground.api.hashtag.entity.QHashtagEntity;
import com.playground.api.restaurant.entity.QRstrntMenuEntity;
import com.playground.api.restaurant.entity.QRstrntMenuHashtagMapngEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.model.RstrntMenuHashtagResponse;
import com.playground.api.restaurant.model.RstrntMenuResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RstrntMenuRepositoryImpl implements RstrntMenuRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QRstrntMenuEntity tbRstrntMenu = QRstrntMenuEntity.rstrntMenuEntity;
  QRstrntMenuHashtagMapngEntity rstrntMenuHashtagMapngEntity = QRstrntMenuHashtagMapngEntity.rstrntMenuHashtagMapngEntity;
  QHashtagEntity hashtagEntity = QHashtagEntity.hashtagEntity;

  @Override
  public List<RstrntMenuResponse> findAll(RstrntMenuEntity entity) {
    List<RstrntMenuResponse> resultList = queryFactory.select(tbRstrntMenu).from(tbRstrntMenu).leftJoin(rstrntMenuHashtagMapngEntity)
        .on(tbRstrntMenu.rstrntSn.eq(rstrntMenuHashtagMapngEntity.rstrntSn), tbRstrntMenu.rstrntMenuSn.eq(rstrntMenuHashtagMapngEntity.rstrntMenuSn))
        .leftJoin(hashtagEntity).on(
            rstrntMenuHashtagMapngEntity.hashtagSn.eq(hashtagEntity.hashtagSn))
        .where(tbRstrntMenu.rstrntSn.eq(entity.getRstrntSn()), rstrntMenuNmLike(entity
            .getRstrntMenuNm()), rstrntMenuPcEq(
                entity.getRstrntMenuPc()))
        .transform(GroupBy.groupBy(tbRstrntMenu.rstrntSn, tbRstrntMenu.rstrntMenuSn)
            .list(Projections.fields(RstrntMenuResponse.class, tbRstrntMenu.rstrntSn.as("restaurantSerialNo"),
                tbRstrntMenu.rstrntMenuSn.as("restaurantMenuSerialNo"), tbRstrntMenu.rstrntMenuNm.as("menuName"),
                tbRstrntMenu.rstrntMenuPc.as("menuPrice"), GroupBy.list(Projections.fields(RstrntMenuHashtagResponse.class,
                    hashtagEntity.hashtagSn.as("hashtagSerialNo"), hashtagEntity.hashtagNm.as("hashtagName"))).as("menuHashtagList"))));

    return resultList.stream().map(menuResponse -> {
      if (menuResponse.getMenuHashtagList().stream().anyMatch(hashtag -> hashtag.getHashtagSerialNo() == null)) {
        menuResponse.setMenuHashtagList(List.of());
      } else {
        menuResponse.getMenuHashtagList().stream().forEach(hashtag -> {
          hashtag.setRestaurantSerialNo(menuResponse.getRestaurantSerialNo());
          hashtag.setRestaurantMenuSerialNo(menuResponse.getRestaurantMenuSerialNo());
        });
      }

      return menuResponse;
    }).toList();
  }

  private BooleanExpression rstrntMenuNmLike(String rstrntMenuNm) {
    return StringUtils.isNotBlank(rstrntMenuNm) ? tbRstrntMenu.rstrntMenuNm.like(rstrntMenuNm) : null;
  }

  private BooleanExpression rstrntMenuPcEq(BigDecimal rstrntMenuPc) {
    return rstrntMenuPc != null ? tbRstrntMenu.rstrntMenuPc.eq(rstrntMenuPc) : null;
  }
}
