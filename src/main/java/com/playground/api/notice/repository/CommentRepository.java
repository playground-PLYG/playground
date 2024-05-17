package com.playground.api.notice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.notice.entity.CommentEntity;
import com.playground.api.notice.entity.CommentEntityPK;
import com.playground.api.notice.repository.dsl.CommentRepositoryCustom;

public interface CommentRepository extends JpaRepository<CommentEntity, CommentEntityPK >, CommentRepositoryCustom{
	List<CommentEntity> deleteByCmntNo(Integer cmntNo);
	List<CommentEntity> deleteByUpperCmntNo(Integer upperCmntNo);
}
