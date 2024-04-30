package com.playground.api.hashtag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.playground.api.hashtag.entity.HashtagEntity;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, String>, JpaSpecificationExecutor<HashtagEntity>{

  List<HashtagEntity> findAll();
  
  HashtagEntity findByHashtagSn(Integer hashtagSn);
}
