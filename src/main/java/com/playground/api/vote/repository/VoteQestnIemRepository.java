package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.VoteQestnIemEntity;
import com.playground.api.vote.entity.VoteQestnIemPK;

@Repository
public interface VoteQestnIemRepository extends JpaRepository<VoteQestnIemEntity, VoteQestnIemPK> {


}
