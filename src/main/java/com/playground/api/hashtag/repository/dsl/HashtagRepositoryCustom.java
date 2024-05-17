package com.playground.api.hashtag.repository.dsl;

import java.util.List;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity;

public interface HashtagRepositoryCustom {
  List<HashtagEntity> findRstrntMenuHashtag(RstrntMenuEntity rstrntMenuEntity);

  List<HashtagEntity> findRstrntMenuRecommend(Integer rstrntSn, Integer rstrntMenuSn);

  List<HashtagEntity> findByHashtagNmContains(Integer rstrntSn, Integer rstrntMenuSn, String hashtagNm);
}
