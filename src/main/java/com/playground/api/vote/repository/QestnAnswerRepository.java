package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.QestnAnswerEntity;
import com.playground.api.vote.entity.QestnAnswerPK;
import com.playground.api.vote.repository.dsl.QestnAnswerRepositoryCustom;

@Repository
public interface QestnAnswerRepository extends JpaRepository<QestnAnswerEntity, QestnAnswerPK>, QestnAnswerRepositoryCustom {

}
