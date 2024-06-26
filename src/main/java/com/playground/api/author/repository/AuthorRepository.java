package com.playground.api.author.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.author.entity.AuthorEntity;
import com.playground.api.author.repository.dsl.AuthorRepositoryCustom;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, String>, AuthorRepositoryCustom {

  List<AuthorEntity> findAll();
}
