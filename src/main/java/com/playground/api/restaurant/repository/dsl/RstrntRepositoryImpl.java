package com.playground.api.restaurant.repository.dsl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.playground.api.file.entity.QFileEntity;
import com.playground.api.restaurant.entity.QRstrntEntity;
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
  QFileEntity tbFile = QFileEntity.fileEntity;

  @Override
  public List<RstrntSrchResponse> findAll(RstrntEntity entity) {
    return queryFactory
        .select(Projections.fields(RstrntSrchResponse.class, tbRstrnt.rstrntSn.as("restaurantSerialNo"), tbRstrnt.rstrntNm.as("restaurantName"),
            tbRstrnt.kakaoMapId, tbRstrnt.laLc.as("la"), tbRstrnt.loLc.as("lo"), tbRstrnt.rstrntKndCode.as("restaurantKindCode"),
            tbRstrnt.rstrntDstnc.as("restaurantDistance"), tbRstrnt.recentChoiseDt.as("recentChoiseDate"),
            tbRstrnt.rstrntImageFileSn.as("imageFileId")))
        .where(rstrntNmLike(entity.getRstrntNm()), rstrntKndCodeEq(entity.getRstrntKndCode())).from(tbRstrnt).leftJoin(tbFile)
        .on(tbRstrnt.rstrntImageFileSn.eq(tbFile.fileSn)).orderBy(tbRstrnt.rstrntSn.desc()).fetch();
  }

  private BooleanExpression rstrntNmLike(String rstrntNm) {
    return StringUtils.isNotBlank(rstrntNm) ? tbRstrnt.rstrntNm.like(rstrntNm) : null;
  }

  private BooleanExpression rstrntKndCodeEq(String rstrntKndCode) {
    return StringUtils.isNotBlank(rstrntKndCode) ? tbRstrnt.rstrntKndCode.eq(rstrntKndCode) : null;
  }

  @Override
  public RstrntDetailSrchResponse findByIdDetail(Integer rstrntSn) {
    return queryFactory
        .select(Projections.fields(RstrntDetailSrchResponse.class, tbRstrnt.rstrntSn.as("restaurantSerialNo"), tbRstrnt.rstrntNm.as("restaurantName"),
            tbRstrnt.kakaoMapId, tbRstrnt.laLc.as("la"), tbRstrnt.loLc.as("lo"), tbRstrnt.rstrntKndCode.as("restaurantKindCode"),
            tbRstrnt.rstrntDstnc.as("restaurantDistance"), tbRstrnt.recentChoiseDt.as("recentChoiseDate"),
            tbRstrnt.rstrntImageFileSn.as("imageFileId")))
        .where(tbRstrnt.rstrntSn.eq(rstrntSn)).from(tbRstrnt).leftJoin(tbFile).on(tbRstrnt.rstrntImageFileSn.eq(tbFile.fileSn)).fetchOne();
  }

  @Override
  public long updateRstrntImageFileSnById(Integer rstrntSn, Integer rstrntImageFileSn) {
    return queryFactory.update(tbRstrnt).set(tbRstrnt.rstrntImageFileSn, rstrntImageFileSn).where(tbRstrnt.rstrntSn.eq(rstrntSn)).execute();
  }
}
