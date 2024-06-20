package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.VoteIemEntity;
import com.playground.api.vote.entity.VoteIemPK;

@Repository
public interface VoteIemRepository extends JpaRepository<VoteIemEntity, VoteIemPK> {


}
