package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.QestnRequest;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {
  private final VoteRepository voteRepository;
  private final ModelMapper modelMapper;
  
  @Transactional(readOnly = true)
  public Page<VoteResponse> getVotePageList(VoteRequest reqData, Pageable pageable) {
    Page<VoteEntity> votePageList = voteRepository.getVotePageList(reqData, pageable);
    List<VoteResponse> voteList = votePageList.getContent().stream()
        .map(entity -> VoteResponse.builder()
                        .voteSsno(entity.getVoteSn())
                        .voteKindCode(entity.getVoteKndCode())
                        .voteSubject(entity.getVoteSj())
                        .anonymityVoteAlternative(entity.getAnnymtyVoteAt())
                        .voteBeginDate(entity.getVoteBeginDt())
                        .voteEndDate(entity.getVoteEndDt())
                        .voteDeleteAlternative(entity.getVoteDeleteAt()).build())
                        .toList();
    return new PageImpl<>(voteList, votePageList.getPageable(), votePageList.getTotalElements());
  }

  @Transactional(readOnly = true)
  public VoteResponse getVoteDetail(VoteRequest reqData) {
    if(!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());
      
      modelMapper.typeMap(VoteEntity.class, VoteResponse.class).addMappings(mapper -> {
        mapper.map(VoteEntity :: getVoteSn, VoteResponse :: setVoteSsno);
        mapper.map(VoteEntity :: getVoteKndCode, VoteResponse :: setVoteKindCode);
        mapper.map(VoteEntity :: getVoteSj, VoteResponse :: setVoteSubject);
        mapper.map(VoteEntity :: getAnnymtyVoteAt, VoteResponse :: setAnonymityVoteAlternative);
        mapper.map(VoteEntity :: getVoteBeginDt, VoteResponse :: setVoteBeginDate);
        mapper.map(VoteEntity :: getVoteEndDt, VoteResponse :: setVoteEndDate);
        mapper.map(VoteEntity :: getVoteDeleteAt, VoteResponse :: setVoteDeleteAlternative);
      });
      
      VoteResponse voteResponse = modelMapper.map(voteEntity, VoteResponse.class);
      
      if(!ObjectUtils.isEmpty(reqData.getQuestionSsno())) {
        List<QestnResponse> qestnResponseList = voteRepository.getQestnDetail(reqData.getVoteSsno(), reqData.getQuestionSsno());
        if(qestnResponseList.size() != 0 ) {
          voteResponse.setQestnResponseList(qestnResponseList);
        }
      }
      
      log.debug("voteResponse ::::: {}", voteResponse);
      return voteResponse;
    }else {
      return new VoteResponse();
    }
    
  }
  
  @Transactional
  public VoteResponse addVote(VoteRequest reqData) {
    log.debug("voteRequestData :::: {}", reqData);
     VoteEntity res= voteRepository.save(VoteEntity.builder()
        .voteKndCode(reqData.getVoteKindCode())
        .voteSj(reqData.getVoteSubject())
        .annymtyVoteAt(reqData.getAnonymityVoteAlternative())
        .voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate()))
        .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate()))
        .voteDeleteAt(reqData.getVoteDeleteAlternative())
        .build());
    
    if(reqData.getQestnRequestList().size() != 0) {
      List<QestnRequest> qestnReList = reqData.getQestnRequestList();
      qestnReList.forEach(qestn -> {
         QestnResponse qestnRes = voteRepository.addQestnDetail(QestnEntity.builder()
            .voteSn(res.getVoteSn())
            .qestnCn(qestn.getQuestionContents())
            .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
            .build());
         log.debug("qestnRes :::: {}", qestnRes);
//        if(qestn.getVoteIemRequestList().size() != 0 ) {
//          qestn.getVoteIemRequestList().forEach(voteIem -> {
//            voteRepository.addVoteIemDetail(VoteIemEntity.builder()
//                .voteSn(qestnRes.getVoteSsno())
//                .qestnSn(qestnRes.getQuestionSsno())
//                .iemId(voteIem.getItemId())
//                .iemNm(voteIem.getItemName())
//                .build());
//          });
//        }
      });
    }


    String tempExcuteResult = "1";
    return VoteResponse.builder().excuteResult(tempExcuteResult).build();
  }

  public VoteResponse modifyVote(VoteRequest reqData) {



    String tempExcuteResult = "1";
    return VoteResponse.builder().excuteResult(tempExcuteResult).build();
  }

  public VoteResponse removeVote(VoteRequest reqData) {



    String tempExcuteResult = "1";
    return VoteResponse.builder().excuteResult(tempExcuteResult).build();
  }
  
  private LocalDateTime stringToLocalDateTime(String strDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
    return dateTime;
  }
}
