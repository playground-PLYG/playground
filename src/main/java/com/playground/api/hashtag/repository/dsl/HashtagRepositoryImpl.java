package com.playground.api.hashtag.repository.dsl;

import java.util.List;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.hashtag.entity.QHashtagEntity;
import com.playground.api.restaurant.entity.QRstrntMenuHashtagMapngEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  QHashtagEntity tbHashtag = QHashtagEntity.hashtagEntity;
  QRstrntMenuHashtagMapngEntity tbRstrntMenuHashtagMapng = QRstrntMenuHashtagMapngEntity.rstrntMenuHashtagMapngEntity;

  @Override
  public List<HashtagEntity> findRstrntMenuHashtag(RstrntMenuEntity rstrntMenuEntity) {
    return queryFactory.selectFrom(tbHashtag).innerJoin(tbRstrntMenuHashtagMapng).on(tbHashtag.hashtagSn.eq(tbRstrntMenuHashtagMapng.hashtagSn))
        .where(tbRstrntMenuHashtagMapng.rstrntSn.eq(rstrntMenuEntity.getRstrntSn()),
            tbRstrntMenuHashtagMapng.rstrntMenuSn.eq(rstrntMenuEntity.getRstrntMenuSn()))
        .fetch();
  }

  @Override
  public List<HashtagEntity> findRstrntMenuRecommend(Integer rstrntSn, Integer rstrntMenuSn) {
    return queryFactory.selectFrom(tbHashtag).innerJoin(tbRstrntMenuHashtagMapng).on(tbHashtag.hashtagSn.eq(tbRstrntMenuHashtagMapng.hashtagSn))
        .where(tbHashtag.hashtagSn.notIn(JPAExpressions.select(tbRstrntMenuHashtagMapng.hashtagSn).from(tbRstrntMenuHashtagMapng)
            .where(tbRstrntMenuHashtagMapng.rstrntSn.eq(rstrntSn), tbRstrntMenuHashtagMapng.rstrntMenuSn.eq(rstrntMenuSn))))
        .groupBy(tbHashtag.hashtagSn).orderBy(tbHashtag.count().desc(), tbHashtag.hashtagSn.desc()).limit(30).fetch();
  }

  @Override
  public List<HashtagEntity> findByHashtagNmContains(Integer rstrntSn, Integer rstrntMenuSn, String hashtagNm) {
    return queryFactory.selectFrom(tbHashtag)
        .where(tbHashtag.hashtagNm.contains(hashtagNm),
            tbHashtag.hashtagSn.notIn(JPAExpressions.select(tbRstrntMenuHashtagMapng.hashtagSn).from(tbRstrntMenuHashtagMapng)
                .where(tbRstrntMenuHashtagMapng.rstrntSn.eq(rstrntSn), tbRstrntMenuHashtagMapng.rstrntMenuSn.eq(rstrntMenuSn))))
        .fetch();
  }
}
