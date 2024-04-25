package com.playground.api.vote.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.playground.api.vote.entity.VoteEntity;

public interface VoteRepository extends CrudRepository<VoteEntity, Integer>{
	List<VoteEntity> findAll();
	
	
	
	
}
