package com.playground.api.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.comment.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer >{
	List<CommentEntity> findAllByNttNo(int nttNo);
	
	List<CommentEntity> deleteByCmntNo(int cmntNo);
	List<CommentEntity> deleteByUpperCmntNo(int upperCmntNo);
}
