package com.playground.api.game.controller;


import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.game.model.GameRankAddRequest;
import com.playground.api.game.model.GameRankSrchRequest;
import com.playground.api.game.model.GameRankSrchResponse;
import com.playground.api.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "game", description = "게임 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class GameController {
  private final GameService gameService;

  /**
   * 랭킹 조회
   */
  @Operation(summary = "랭킹 조회", description = "랭킹 조회")
  @PostMapping("/public/game/getRankList")
  public List<GameRankSrchResponse> getRankList(@RequestBody @Valid GameRankSrchRequest reqData) {
    return gameService.getRankList(reqData);
  }

  /**
   * 점수 저장
   */
  @Operation(summary = "점수 저장", description = "점수 저장")
  @PostMapping("/public/game/addRank")
  public void addRank(@RequestBody @Valid GameRankAddRequest reqData) {
    gameService.addRank(reqData);
  }
}


