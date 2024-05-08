package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.repository.dsl.VoteRepositoryCustom;

public interface VoteRepository extends JpaRepository<VoteEntity, String>, VoteRepositoryCustom{
 

}
