package com.playground.api.author.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.author.entity.MberAuthorEntity;
import com.playground.api.author.repository.dsl.MberAuthorRepositoryCustom;

@Repository
public interface MberAuthorRepository extends JpaRepository<MberAuthorEntity, String>, MberAuthorRepositoryCustom {

  List<MberAuthorEntity> findAll();
}
