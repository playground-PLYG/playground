package com.playground.api.member.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.playground.api.member.entity.PgMemberEntity;

@Repository
public interface PgMemberRepository extends JpaRepository<PgMemberEntity, String>, JpaSpecificationExecutor<PgMemberEntity> {

  PgMemberEntity findByMbrEmlAddr(String email);
  
  List<PgMemberEntity> findAll(Specification<PgMemberEntity> searchCondition);

}
