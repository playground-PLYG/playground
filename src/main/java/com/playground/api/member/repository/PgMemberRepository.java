package com.playground.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playground.api.member.entity.PgMemberEntity;

@Repository
public interface PgMemberRepository extends JpaRepository<PgMemberEntity, String> {

  PgMemberEntity findByMbrEmlAddr(String email);

}
