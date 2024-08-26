package com.playground.api.game.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.game.entity.GameResultEntity;
import com.playground.api.game.model.GameRankAddRequest;
import com.playground.api.game.model.GameRankSrchRequest;
import com.playground.api.game.model.GameRankSrchResponse;
import com.playground.api.game.repository.GameResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
  private final GameResultRepository gameResultRepository;

  @Transactional(readOnly = true)
  public List<GameRankSrchResponse> getRankList(GameRankSrchRequest reqData) {
    GameResultEntity entity = GameResultEntity.builder().gameTyCode(reqData.getGameTyCode()).build();

    List<GameResultEntity> gameResultEntityList = gameResultRepository.findRankList(entity);

    if (CollectionUtils.isNotEmpty(gameResultEntityList)) {
      return gameResultEntityList.stream()
          .map(gameRankentity -> GameRankSrchResponse.builder().gameTyCode(gameRankentity.getGameTyCode()).ncmCn(gameRankentity.getNcmCn())
              .gameTime(gameRankentity.getGameTime()).gameOneAtrbCn(gameRankentity.getGameOneAtrbCn())
              .gameTwoAtrbCn(gameRankentity.getGameTwoAtrbCn()).gameThreeAtrbCn(gameRankentity.getGameThreeAtrbCn())
              .gameFourAtrbCn(gameRankentity.getGameFourAtrbCn()).gameFiveAtrbCn(gameRankentity.getGameFiveAtrbCn()).build())
          .toList();
    } else {
      return new ArrayList<>();
    }
  }

  @Transactional
  public void addRank(GameRankAddRequest reqData) {
    GameResultEntity entity = GameResultEntity.builder().gameTyCode(reqData.getGameTyCode()).ncmCn(reqData.getNcmCn()).gameTime(reqData.getGameTime())
        .gameOneAtrbCn(reqData.getGameOneAtrbCn()).gameTwoAtrbCn(reqData.getGameTwoAtrbCn()).gameThreeAtrbCn(reqData.getGameThreeAtrbCn())
        .gameFourAtrbCn(reqData.getGameFourAtrbCn()).gameFiveAtrbCn(reqData.getGameFiveAtrbCn()).build();

    gameResultRepository.save(entity);
  }
}
