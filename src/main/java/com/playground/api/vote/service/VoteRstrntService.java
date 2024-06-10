package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.service.RstrntService;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteIemEntity;
import com.playground.api.vote.model.VoteRstrntResponse;
import com.playground.api.vote.repository.QestnRepository;
import com.playground.api.vote.repository.VoteIemRepository;
import com.playground.api.vote.repository.VoteRepository;
import com.playground.api.vote.repository.dsl.VoteRstrntRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteRstrntService {
  private final VoteRepository voteRepository;
  private final QestnRepository qestnRepository;
  private final VoteIemRepository voteIemRepository;
  private final RstrntService rstrntService;
  private final VoteRstrntRepositoryCustom voteRstrntRepositoryCustom;

  @Transactional
  public void addTodayLunchVote() {
    LocalDateTime now = LocalDateTime.now();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formatedNow = now.format(formatter);
    
    //1. tb_vote 테이블 등록
    VoteEntity resVote = voteRepository.save(VoteEntity.builder()
        .voteKndCode("LUN")
        .voteSj(formatedNow + " 점심 투표")
        .annymtyVoteAt("N")
        .voteBeginDt(now)
        .voteEndDt(now.plusDays(1))
        .voteDeleteAt("N")
        .build());
    
    
    //2. tb_qestn 테이블 등록
    QestnEntity resQestn = qestnRepository.save(QestnEntity.builder()
        .voteSn(resVote.getVoteSn())
        .qestnCn("식당 투표 목록")
        .compnoChoiseAt("N")
        .build());
    
    //3. tb_vote_iem 테이블 등록
    List<VoteIemEntity> saveIemList = new ArrayList<>(); //한번에 insert 하기 위한 List
    
    RstrntSrchRequest req = new RstrntSrchRequest();
    List<RstrntSrchResponse> resRstrntList = rstrntService.getRstrntList(req); // 식당 메뉴 조회
    
    for(RstrntSrchResponse res : resRstrntList) { // 조회한 식당리스트를 등록하기 위해 변환
      saveIemList.add(
      VoteIemEntity.builder()
      .voteSn(resVote.getVoteSn())
      .qestnSn(resQestn.getQestnSn())
      .iemSn(res.getRestaurantSerialNo()) // 이거 DB 변경해야할듯
      .iemNm(res.getRestaurantName())
      .build());
    }
    
    List<VoteIemEntity> resVoteIem = voteIemRepository.saveAll(saveIemList);
    
  }
  
  public List<VoteRstrntResponse> getVoteRstrntList() {
    return voteRstrntRepositoryCustom.getVoteRstrntList();
  }
}
