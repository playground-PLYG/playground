package com.playground.api.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.post.entity.PostEntity;

public interface PostRepository extends JpaRepository< PostEntity, Integer >{
	List<PostEntity> findAllByBbsId(String postNo);
	
	List<PostEntity> deleteByNttNo(int nttNo);
}
