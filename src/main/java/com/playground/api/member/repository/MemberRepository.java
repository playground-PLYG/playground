package com.playground.api.member.repository;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.playground.api.member.entity.MberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MberEntity, String>, JpaSpecificationExecutor<MberEntity> {

  MberEntity findByMberIdOrMberEmailAdres(String userId, String email);

  MberEntity findByMberNm(String name);

  MberEntity findByMberEmailAdres(String email);

  List<MberEntity> findAll(Specification<MberEntity> searchCondition);

}
