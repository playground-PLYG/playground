package com.playground.api.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.notice.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer >{
	List<CommentEntity> findAllByNttNo(int nttNo);
	
	List<CommentEntity> deleteByCmntNo(int cmntNo);
	List<CommentEntity> deleteByUpperCmntNo(int upperCmntNo);
}
