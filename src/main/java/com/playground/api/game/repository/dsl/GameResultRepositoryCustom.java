package com.playground.api.game.repository.dsl;

import java.util.List;
import com.playground.api.game.entity.GameResultEntity;

public interface GameResultRepositoryCustom {
  List<GameResultEntity> findRankList(GameResultEntity entity);

}
