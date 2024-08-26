package com.playground.api.game.repository.dsl;

import java.util.List;
import com.playground.api.game.constants.GameType;
import com.playground.api.game.entity.GameResultEntity;
import com.playground.api.game.entity.QGameResultEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameResultRepositoryImpl implements GameResultRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  QGameResultEntity tbGameResult = QGameResultEntity.gameResultEntity;

  @Override
  public List<GameResultEntity> findRankList(GameResultEntity entity) {
    String gameTyCode = entity.getGameTyCode();

    JPAQuery<GameResultEntity> query = queryFactory.selectFrom(tbGameResult).where(tbGameResult.gameTyCode.eq(gameTyCode));

    if (GameType.TETRIS.name().equals(gameTyCode)) {
      query.orderBy(tbGameResult.gameOneAtrbCn.desc(), tbGameResult.gameTime.asc()); // 점수 desc, 시간 asc
    } else if (GameType.SUDOKU.name().equals(gameTyCode)) {
      query.orderBy(tbGameResult.gameOneAtrbCn.desc(), tbGameResult.gameTime.asc()); // 난이도 desc, 시간 asc
    } else {
      query.orderBy(tbGameResult.gameTime.asc()); // 난이도 desc, 시간 asc
    }

    return query.limit(100).fetch();
  }
}
