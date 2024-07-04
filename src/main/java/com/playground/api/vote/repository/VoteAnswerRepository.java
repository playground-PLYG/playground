package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.VoteAnswerEntity;
import com.playground.api.vote.entity.VoteAnswerPK;
import com.playground.api.vote.repository.dsl.VoteAnswerRepositoryCustom;

@Repository
public interface VoteAnswerRepository extends JpaRepository<VoteAnswerEntity, VoteAnswerPK>, VoteAnswerRepositoryCustom {

}
