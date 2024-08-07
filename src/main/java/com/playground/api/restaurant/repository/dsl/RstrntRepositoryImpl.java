

package com.playground.api.restaurant.repository.dsl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.playground.api.restaurant.entity.QRstrntEntity;
import com.playground.api.restaurant.entity.QRstrntFileEntity;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.model.RstrntDetailSrchResponse;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RstrntRepositoryImpl implements RstrntRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QRstrntEntity tbRstrnt = QRstrntEntity.rstrntEntity;
  QRstrntFileEntity tbRstrntFile = QRstrntFileEntity.rstrntFileEntity;

  @Override
  public List<RstrntSrchResponse> findAll(RstrntEntity entity) {
    return queryFactory.select(tbRstrnt).from(tbRstrnt).leftJoin(tbRstrntFile).on(tbRstrnt.rstrntSn.eq(tbRstrntFile.rstrntSn))
        .where(rstrntNmLike(entity.getRstrntNm()), rstrntKndCodeEq(entity.getRstrntKndCode())).orderBy(tbRstrnt.rstrntSn.asc())
        .transform(groupBy(tbRstrnt.rstrntSn)
            .list(Projections.fields(RstrntSrchResponse.class, tbRstrnt.rstrntSn.as("restaurantSerialNo"), tbRstrnt.rstrntNm.as("restaurantName"),
                tbRstrnt.kakaoMapId.as("kakaoMapId"), tbRstrnt.laLc.as("la"), tbRstrnt.loLc.as("lo"), tbRstrnt.rstrntKndCode.as("restaurantKindCode"),
                tbRstrnt.rstrntDstnc.as("restaurantDistance"), list(tbRstrntFile.fileSn).as("imageFileIds"))));
  }

  private BooleanExpression rstrntNmLike(String rstrntNm) {
    return StringUtils.isNotBlank(rstrntNm) ? tbRstrnt.rstrntNm.like("%" + rstrntNm + "%") : null;
  }

  private BooleanExpression rstrntKndCodeEq(String rstrntKndCode) {
    return StringUtils.isNotBlank(rstrntKndCode) ? tbRstrnt.rstrntKndCode.eq(rstrntKndCode) : null;
  }

  @Override
  public RstrntDetailSrchResponse findByIdDetail(Integer rstrntSn) {
    return queryFactory.select(Projections.fields(RstrntDetailSrchResponse.class, tbRstrnt.rstrntSn.as("restaurantSerialNo"),
        tbRstrnt.rstrntNm.as("restaurantName"), tbRstrnt.kakaoMapId, tbRstrnt.laLc.as("la"), tbRstrnt.loLc.as("lo"),
        tbRstrnt.rstrntKndCode.as("restaurantKindCode"), tbRstrnt.rstrntDstnc.as("restaurantDistance"))).where(tbRstrnt.rstrntSn.eq(rstrntSn))
        .from(tbRstrnt).fetchOne();
  }


}
