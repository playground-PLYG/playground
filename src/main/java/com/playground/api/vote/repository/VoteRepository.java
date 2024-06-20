package com.playground.api.vote.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.repository.dsl.VoteRepositoryCustom;

public interface VoteRepository extends JpaRepository<VoteEntity, Integer>, VoteRepositoryCustom {

  Optional<VoteEntity> findById(Integer voteSn);

}
