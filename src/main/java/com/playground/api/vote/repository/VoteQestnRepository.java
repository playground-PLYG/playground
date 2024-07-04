package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.VoteQestnEntity;
import com.playground.api.vote.entity.VoteQestnPK;

@Repository
public interface VoteQestnRepository extends JpaRepository<VoteQestnEntity, VoteQestnPK> {


}
