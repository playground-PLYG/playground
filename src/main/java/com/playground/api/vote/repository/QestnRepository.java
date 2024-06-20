package com.playground.api.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.QestnPK;

@Repository
public interface QestnRepository extends JpaRepository<QestnEntity, QestnPK> {


}
