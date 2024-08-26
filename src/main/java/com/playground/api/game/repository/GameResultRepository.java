package com.playground.api.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.game.entity.GameResultEntity;
import com.playground.api.game.repository.dsl.GameResultRepositoryCustom;

public interface GameResultRepository extends JpaRepository<GameResultEntity, Integer>, GameResultRepositoryCustom {

}
