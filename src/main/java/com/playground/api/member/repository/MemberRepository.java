package com.playground.api.member.repository;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.playground.api.member.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String>, JpaSpecificationExecutor<MemberEntity> {

  MemberEntity findByMberIdOrMberEmailAdres(String userId, String email);

  MemberEntity findByMberNm(String name);

  MemberEntity findByMberEmailAdres(String email);

  List<MemberEntity> findAll(Specification<MemberEntity> searchCondition);

}
