package com.playground.api.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.board.entity.CommentEntity;
import com.playground.api.board.entity.CommentEntityPK;
import com.playground.api.board.repository.dsl.CommentRepositoryCustom;

public interface CommentRepository extends JpaRepository<CommentEntity, CommentEntityPK >, CommentRepositoryCustom{
	List<CommentEntity> deleteByCmntSn(Integer cmntNo);
	List<CommentEntity> deleteByUpperCmntSn(Integer upperCmntNo);
}
