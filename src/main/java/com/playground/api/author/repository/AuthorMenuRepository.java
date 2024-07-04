package com.playground.api.author.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.author.entity.AuthorMenuEntity;
import com.playground.api.author.entity.AuthorMenuEntityPK;
import com.playground.api.author.repository.dsl.AuthorMenuRepositoryCustom;

@Repository
public interface AuthorMenuRepository extends JpaRepository<AuthorMenuEntity, AuthorMenuEntityPK>, AuthorMenuRepositoryCustom {

  List<AuthorMenuEntity> findAll();
}
