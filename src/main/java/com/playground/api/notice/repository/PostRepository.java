package com.playground.api.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.notice.entity.PostEntity;

public interface PostRepository extends JpaRepository< PostEntity, Integer >{
	List<PostEntity> findAllByBbsId(String postNo);
	
	List<PostEntity> deleteByNttNo(int nttNo);
}
