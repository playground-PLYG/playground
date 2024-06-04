package com.playground.api.assets.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.assets.entity.AssetsEntity;
import com.playground.api.assets.model.AssetsResponse;

public interface AssetsRepository extends JpaRepository<AssetsEntity, Integer>{

  Page<AssetsEntity> findAll(Pageable pageable);

  AssetsEntity findByAssetsSn(Integer req);
   
}
