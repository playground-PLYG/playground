package com.playground.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.playground.api.member.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String>, JpaSpecificationExecutor<MemberEntity> {

  MemberEntity findByUserIdOrEmail(String userId, String email);

  MemberEntity findByName(String name);
 
}
