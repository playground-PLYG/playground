package com.playground.api.author.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.playground.api.author.entity.AuthorEntity;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, String>, JpaSpecificationExecutor<AuthorEntity> {

  List<AuthorEntity> findAll();
}
