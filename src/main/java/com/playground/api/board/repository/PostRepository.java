package com.playground.api.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.board.entity.NoticeEntity;
import com.playground.api.board.entity.PostEntity;
import com.playground.api.board.entity.PostEntityPK;

public interface PostRepository extends JpaRepository<PostEntity, PostEntityPK> {

  List<PostEntity> findByNoticeEntity(NoticeEntity noticeEntity);

  List<PostEntity> deleteByNttSn(Integer nttNo);
}
