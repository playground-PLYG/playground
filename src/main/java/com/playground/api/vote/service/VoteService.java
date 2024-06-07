package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.service.RstrntService;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteIemEntity;
import com.playground.api.vote.model.QestnRequest;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteIemResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.repository.QestnRepository;
import com.playground.api.vote.repository.VoteIemRepository;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {
  private final VoteRepository voteRepository;
  private final QestnRepository qestnRepository;
  private final VoteIemRepository voteIemRepository;
  private final ModelMapper modelMapper;
  private final RstrntService rstrntService;

  @Transactional(readOnly = true)
  public Page<VoteResponse> getVotePageList(VoteRequest reqData, Pageable pageable) {
    log.debug("VoteService.getVotePageList ::::: voteRequest ::::: {}", reqData);
    Page<VoteEntity> votePageList = voteRepository.getVotePageList(reqData, pageable);
    List<VoteResponse> voteList = votePageList.getContent().stream()
        .map(entity -> VoteResponse.builder()
            .voteSsno(entity.getVoteSn())
            .voteKindCode(entity.getVoteKndCode())
            .voteSubject(entity.getVoteSj())
            .anonymityVoteAlternative(entity.getAnnymtyVoteAt())
            .voteBeginDate(entity.getVoteBeginDt())
            .voteEndDate(entity.getVoteEndDt())
            .voteDeleteAlternative(entity.getVoteDeleteAt())
            .registUsrId(entity.getRegistUsrId())
            .registDt(entity.getRegistDt())
            .updtUsrId(entity.getUpdtUsrId())
            .updtDt(entity.getUpdtDt())
            .build())
        .toList();
    return new PageImpl<>(voteList, votePageList.getPageable(), votePageList.getTotalElements());
  }

  @Transactional(readOnly = true)
  public VoteResponse getVoteDetail(VoteRequest reqData) {
    log.debug("VoteService.getVoteDetail ::::: voteRequest ::::: {}", reqData);
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());

      modelMapper.typeMap(VoteEntity.class, VoteResponse.class).addMappings(mapper -> {
        mapper.map(VoteEntity::getVoteSn, VoteResponse::setVoteSsno);
        mapper.map(VoteEntity::getVoteKndCode, VoteResponse::setVoteKindCode);
        mapper.map(VoteEntity::getVoteSj, VoteResponse::setVoteSubject);
        mapper.map(VoteEntity::getAnnymtyVoteAt, VoteResponse::setAnonymityVoteAlternative);
        mapper.map(VoteEntity::getVoteBeginDt, VoteResponse::setVoteBeginDate);
        mapper.map(VoteEntity::getVoteEndDt, VoteResponse::setVoteEndDate);
        mapper.map(VoteEntity::getVoteDeleteAt, VoteResponse::setVoteDeleteAlternative);
      });

      VoteResponse voteResponse = modelMapper.map(voteEntity, VoteResponse.class);

      List<QestnResponse> qestnResponseList = voteRepository.getQestnDetail(reqData.getVoteSsno(), reqData.getQuestionSsno());
      if (qestnResponseList.size() != 0) {
        voteResponse.setQestnResponseList(qestnResponseList);
      }

      log.debug("VoteService.getVoteDetail ::::: voteResponse ::::: {}", voteResponse);
      return voteResponse;
    } else {
      return new VoteResponse();
    }

  }

  @Transactional
  public VoteResponse addVote(VoteRequest reqData) {
    log.debug("VoteService.addVote ::::: voteRequestData ::::: {}", reqData);
    VoteResponse voteResponse = new VoteResponse();

    // builder 대신 modelMapper 사용해보기
    modelMapper.typeMap(VoteEntity.class, VoteResponse.class).addMappings(mapper -> {
      mapper.map(VoteEntity::getVoteSn, VoteResponse::setVoteSsno);
      mapper.map(VoteEntity::getVoteKndCode, VoteResponse::setVoteKindCode);
      mapper.map(VoteEntity::getVoteSj, VoteResponse::setVoteSubject);
      mapper.map(VoteEntity::getAnnymtyVoteAt, VoteResponse::setAnonymityVoteAlternative);
      mapper.map(VoteEntity::getVoteBeginDt, VoteResponse::setVoteBeginDate);
      mapper.map(VoteEntity::getVoteEndDt, VoteResponse::setVoteEndDate);
      mapper.map(VoteEntity::getVoteDeleteAt, VoteResponse::setVoteDeleteAlternative);
    });

    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder()
        .voteKndCode(reqData.getVoteKindCode())
        .voteSj(reqData.getVoteSubject())
        .annymtyVoteAt(reqData.getAnonymityVoteAlternative())
        .voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate()))
        .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate()))
        .voteDeleteAt(reqData.getVoteDeleteAlternative())
        .build());

    voteResponse = modelMapper.map(voteEntity, VoteResponse.class);

    List<QestnResponse> setQestnList = new ArrayList<>();
    if(!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
      List<QestnRequest> qestnReList = reqData.getQestnRequestList();
      qestnReList.forEach(qestn -> {
        QestnEntity qestnRes = qestnRepository.save(QestnEntity.builder()
            .voteSn(voteEntity.getVoteSn())
            .qestnCn(qestn.getQuestionContents())
            .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
            .build());

        QestnResponse setQestn = QestnResponse.builder()
            .questionSsno(qestnRes.getQestnSn())
            .voteSsno(qestnRes.getVoteSn())
            .questionContents(qestnRes.getQestnCn())
            .compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt())
            .build();

        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteIemEntity.builder()
                .voteSn(qestnRes.getVoteSn())
                .qestnSn(qestnRes.getQestnSn())
                .iemSn(voteIem.getItemSsno())
                .iemNm(voteIem.getItemName())
                .build());
          });

          List<VoteIemEntity> saveAllIemEntities = voteIemRepository.saveAll(iemEntities);
          setQestn.setVoteIemResponseList(saveAllIemEntities.stream()
              .map(entity -> VoteIemResponse.builder()
                  .itemSsno(entity.getIemSn())
                  .itemName(entity.getIemNm())
                  .build())
              .toList());

        }
        setQestnList.add(setQestn);
      });
      voteResponse.setQestnResponseList(setQestnList);
    }

    return voteResponse;
  }
  
  @Transactional
  public VoteResponse modifyVote(VoteRequest reqData) {
    log.debug("VoteService.modifyVote ::::: voteRequestData ::::: {}", reqData);
    
    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder()
        .voteSn(reqData.getVoteSsno())
        .voteKndCode(reqData.getVoteKindCode())
        .voteSj(reqData.getVoteSubject())
        .annymtyVoteAt(reqData.getAnonymityVoteAlternative())
        .voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate()))
        .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate()))
        .voteDeleteAt(reqData.getVoteDeleteAlternative())
        .build());

   VoteResponse resData = VoteResponse.builder()
       .voteSsno(voteEntity.getVoteSn())
       .voteKindCode(voteEntity.getVoteKndCode())
       .voteSubject(voteEntity.getVoteSj())
       .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
       .voteBeginDate(voteEntity.getVoteBeginDt())
       .voteEndDate(voteEntity.getVoteEndDt())
       .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
       .build();
     
   // voteEntity 에서 기존에 있었던 questionSsno 랑 reqData 로 들어온 queestionSsno랑 비교해서
   List<QestnEntity> prevQestnList = voteRepository.getQestnList(reqData.getVoteSsno());
   List<Integer> prevQestnSnList = new ArrayList<>();
   List<Integer> afterQestnSnList = new ArrayList<>();
   List<Integer> removeQestnSnList = new ArrayList<>();
   if (!ObjectUtils.isEmpty(prevQestnList)) {
     for(QestnEntity prev : prevQestnList) {
       prevQestnSnList.add(prev.getQestnSn());
     }
     
     if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
       for(QestnRequest after : reqData.getQestnRequestList()) {
         afterQestnSnList.add(after.getQuestionSsno());
       }
     }
   }
   // voteEntity 에만 존재하는 questionSsno 는 DB에서 지워주고  + voteIem 도 지워 주고
   if(!ObjectUtils.isEmpty(prevQestnSnList) && !ObjectUtils.isEmpty(afterQestnSnList)) {
     Collections.sort(prevQestnSnList);
     Collections.sort(afterQestnSnList);
     
     removeQestnSnList = prevQestnSnList.stream().filter(prev -> afterQestnSnList.stream()
         .noneMatch(Predicate.isEqual(prev))).collect(Collectors.toList());
   }
   
   if(!ObjectUtils.isEmpty(removeQestnSnList)) {
     Integer reqVoteSsno = reqData.getVoteSsno();
     // voteIem 지우기
     removeQestnSnList.forEach(removeQesSsno -> {
       voteRepository.deleteBySsnoForVoteIem(reqVoteSsno, removeQesSsno);
     });
     
     // qestn 지우기 
     removeQestnSnList.forEach(removeQesSsno -> {
       voteRepository.deleteBySsnoForQestn(reqVoteSsno, removeQesSsno);
     });
   }
   
   List<QestnResponse> setQestnList = new ArrayList<>();
   if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
     List<QestnRequest> qestnReList = reqData.getQestnRequestList();
     qestnReList.forEach(qestn -> {
       QestnEntity qestnRes = qestnRepository.save(QestnEntity.builder()
           .qestnSn(qestn.getQuestionSsno())
           .voteSn(voteEntity.getVoteSn())
           .qestnCn(qestn.getQuestionContents())
           .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
           .build());

       QestnResponse setQestn = QestnResponse.builder()
           .questionSsno(qestnRes.getQestnSn())
           .voteSsno(qestnRes.getVoteSn())
           .questionContents(qestnRes.getQestnCn())
           .compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt())
           .build();

       if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
         List<VoteIemEntity> iemEntities = new ArrayList<>();
         qestn.getVoteIemRequestList().forEach(voteIem -> {
           iemEntities.add(VoteIemEntity.builder()
               .voteSn(qestnRes.getVoteSn())
               .qestnSn(qestnRes.getQestnSn())
               .iemSn(voteIem.getItemSsno())
               .iemNm(voteIem.getItemName())
               .build());
         });
         
         // 기존에 있었던 투표항목 제거 후 등록
         voteRepository.deleteBySsnoForVoteIem(qestnRes.getVoteSn(), qestnRes.getQestnSn());
         
         // 등록
         List<VoteIemEntity> saveAllIemEntities = voteIemRepository.saveAll(iemEntities);
         setQestn.setVoteIemResponseList(saveAllIemEntities.stream()
             .map(entity -> VoteIemResponse.builder()
                 .itemSsno(entity.getIemSn())
                 .itemName(entity.getIemNm())
                 .build())
             .toList());
       }
       setQestnList.add(setQestn);
     });
     resData.setQestnResponseList(setQestnList);
   }
   
   return resData;
  }

  @Transactional
  public VoteResponse removeVote(VoteRequest reqData) {
    log.debug("VoteService.removeVote ::::: voteRequest ::::: {}", reqData);
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      Long upValVote = voteRepository.updateByIdForVote(reqData);
      Long delValQestn = voteRepository.deleteByVoteSnForQestn(reqData.getVoteSsno());
      Long delValVoteIem = voteRepository.deleteByVoteSnForVoteIem(reqData.getVoteSsno());
      log.debug("VoteService.removeVote ::::: delValQestn, delValVoteIem ::::: {}{}", delValQestn,delValVoteIem);
      
      if(upValVote >= 1L) {
        VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());
        return VoteResponse.builder()
            .voteSsno(voteEntity.getVoteSn())
            .voteKindCode(voteEntity.getVoteKndCode())
            .voteSubject(voteEntity.getVoteSj())
            .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
            .voteBeginDate(voteEntity.getVoteBeginDt())
            .voteEndDate(voteEntity.getVoteEndDt())
            .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
            .build();
      }else {
        return new VoteResponse();
      }
    } else {
      return new VoteResponse();
    }
  }

  private LocalDateTime stringToLocalDateTime(String strDate) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
      LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
      return dateTime;
  }
  
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
      .iemId(String.valueOf(res.getRestaurantSerialNo())) // 이거 DB 변경해야할듯
      .iemNm(res.getRestaurantName())
      .build());
    }
    
    List<VoteIemEntity> resVoteIem = voteIemRepository.saveAll(saveIemList);
    
  }
}
