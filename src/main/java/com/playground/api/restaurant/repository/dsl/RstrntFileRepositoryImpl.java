package com.playground.api.restaurant.repository.dsl;

import java.util.List;
import com.playground.api.restaurant.entity.QRstrntFileEntity;
import com.playground.api.restaurant.model.RstrntFileResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RstrntFileRepositoryImpl implements RstrntFileRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QRstrntFileEntity tbRstrntFile = QRstrntFileEntity.rstrntFileEntity;

  public List<RstrntFileResponse> findAllByRstrntSn(Integer rstrntSn) {
    return queryFactory
        .select(Projections.fields(RstrntFileResponse.class, tbRstrntFile.rstrntSn.as("restaurantSerialNo"), tbRstrntFile.fileSn.as("fileSerialNo")))
        .from(tbRstrntFile).where(tbRstrntFile.rstrntSn.eq(rstrntSn)).fetch();
  }


  @Override
  public long updateRstrntImageFileSnById(Integer rstrntSn, Integer rstrntImageFileSn, Integer oldImageFileId) {
    return queryFactory.update(tbRstrntFile).set(tbRstrntFile.fileSn, rstrntImageFileSn)
        .where(tbRstrntFile.rstrntSn.eq(rstrntSn), tbRstrntFile.fileSn.eq(oldImageFileId)).execute();
  }

}
