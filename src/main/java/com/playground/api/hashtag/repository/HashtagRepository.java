package com.playground.api.hashtag.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.hashtag.repository.dsl.HashtagRepositoryCustom;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, Integer>, HashtagRepositoryCustom {
  List<HashtagEntity> findAll();

  Optional<HashtagEntity> findById(Integer hashtagSn);

  HashtagEntity findByHashtagNm(String hashtagNm);
}
