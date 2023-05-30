package com.member.api.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.member.api.sample.entity.PagingEntity;

public interface PagingPageRepository extends CrudRepository<PagingEntity, String> {
  Page<PagingEntity> findAll(Pageable pageable);
}
