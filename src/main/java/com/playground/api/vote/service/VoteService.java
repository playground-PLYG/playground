package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.message.model.DiscordEmbedRequest;
import com.playground.api.message.model.DiscordRequest;
import com.playground.api.message.service.MessageService;
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
  private final MessageService messageService;

  @Value("${CLIENT_URL}")
  private String clientUrl;

  @Transactional(readOnly = true)
  public Page<VoteResponse> getVotePageList(VoteRequest reqData, Pageable pageable) {
    log.debug("VoteService.getVotePageList ::::: voteRequest ::::: {}", reqData);
    Page<VoteEntity> votePageList = voteRepository.getVotePageList(reqData, pageable);
    List<VoteResponse> voteList = votePageList.getContent().stream()
        .map(entity -> VoteResponse.builder().voteSsno(entity.getVoteSn()).voteKindCode(entity.getVoteKndCode()).voteSubject(entity.getVoteSj())
            .anonymityVoteAlternative(entity.getAnnymtyVoteAt()).voteBeginDate(entity.getVoteBeginDt()).voteEndDate(entity.getVoteEndDt())
            .voteDeleteAlternative(entity.getVoteDeleteAt()).registUsrId(entity.getRegistUsrId()).registDt(entity.getRegistDt())
            .updtUsrId(entity.getUpdtUsrId()).updtDt(entity.getUpdtDt()).build())
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

    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder().voteKndCode(reqData.getVoteKindCode()).voteSj(reqData.getVoteSubject())
        .annymtyVoteAt(reqData.getAnonymityVoteAlternative()).voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate()))
        .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate())).voteDeleteAt(reqData.getVoteDeleteAlternative()).build());

    voteResponse = modelMapper.map(voteEntity, VoteResponse.class);

    List<QestnResponse> setQestnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
      List<QestnRequest> qestnReList = reqData.getQestnRequestList();
      qestnReList.forEach(qestn -> {
        QestnEntity qestnRes = qestnRepository.save(QestnEntity.builder().voteSn(voteEntity.getVoteSn()).qestnCn(qestn.getQuestionContents())
            .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());

        QestnResponse setQestn = QestnResponse.builder().questionSsno(qestnRes.getQestnSn()).voteSsno(qestnRes.getVoteSn())
            .questionContents(qestnRes.getQestnCn()).compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt()).build();

        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteIemEntity.builder().voteSn(qestnRes.getVoteSn()).qestnSn(qestnRes.getQestnSn()).iemSn(voteIem.getItemSsno())
                .iemNm(voteIem.getItemName()).build());
          });

          List<VoteIemEntity> saveAllIemEntities = voteIemRepository.saveAll(iemEntities);
          setQestn.setVoteIemResponseList(saveAllIemEntities.stream()
              .map(entity -> VoteIemResponse.builder().itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList());

        }
        setQestnList.add(setQestn);
      });
      voteResponse.setQestnResponseList(setQestnList);
    }

    // discord 메시지 전송
    DiscordRequest dto = new DiscordRequest();
    dto.setApiNm("vote"); // vote 채널의 API_KEY
    dto.setUsername(voteEntity.getRegistUsrId());

    DiscordEmbedRequest embed = new DiscordEmbedRequest();
    embed.setTitle(reqData.getVoteSubject());
    embed.setDescription("[투표하기](" + clientUrl + "/vote?ssno=" + voteEntity.getVoteSn() + ")");
    embed.addField("익명투표여부", "N".equals(reqData.getAnonymityVoteAlternative()) ? "회원투표" : "익명투표", true);

    dto.addEmbed(embed);

    messageService.sendDiscordWebhook(dto);

    return voteResponse;
  }

  @Transactional
  public VoteResponse modifyVote(VoteRequest reqData) {
    log.debug("VoteService.modifyVote ::::: voteRequestData ::::: {}", reqData);

    VoteEntity voteEntity =
        voteRepository.save(VoteEntity.builder().voteSn(reqData.getVoteSsno()).voteKndCode(reqData.getVoteKindCode()).voteSj(reqData.getVoteSubject())
            .annymtyVoteAt(reqData.getAnonymityVoteAlternative()).voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate()))
            .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate())).voteDeleteAt(reqData.getVoteDeleteAlternative()).build());

    VoteResponse resData = VoteResponse.builder().voteSsno(voteEntity.getVoteSn()).voteKindCode(voteEntity.getVoteKndCode())
        .voteSubject(voteEntity.getVoteSj()).anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt()).voteBeginDate(voteEntity.getVoteBeginDt())
        .voteEndDate(voteEntity.getVoteEndDt()).voteDeleteAlternative(voteEntity.getVoteDeleteAt()).build();

    // voteEntity 에서 기존에 있었던 questionSsno 랑 reqData 로 들어온 queestionSsno랑 비교해서
    List<QestnEntity> prevQestnList = voteRepository.getQestnList(reqData.getVoteSsno());
    List<Integer> prevQestnSnList = new ArrayList<>();
    List<Integer> afterQestnSnList = new ArrayList<>();
    List<Integer> removeQestnSnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(prevQestnList)) {
      for (QestnEntity prev : prevQestnList) {
        prevQestnSnList.add(prev.getQestnSn());
      }

      if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
        for (QestnRequest after : reqData.getQestnRequestList()) {
          afterQestnSnList.add(after.getQuestionSsno());
        }
      }
    }
    // voteEntity 에만 존재하는 questionSsno 는 DB에서 지워주고 + voteIem 도 지워 주고
    if (!ObjectUtils.isEmpty(prevQestnSnList) && !ObjectUtils.isEmpty(afterQestnSnList)) {
      Collections.sort(prevQestnSnList);
      Collections.sort(afterQestnSnList);

      removeQestnSnList = prevQestnSnList.stream().filter(prev -> afterQestnSnList.stream().noneMatch(Predicate.isEqual(prev))).toList();
    }

    if (!ObjectUtils.isEmpty(removeQestnSnList)) {
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
        QestnEntity qestnRes = qestnRepository.save(QestnEntity.builder().qestnSn(qestn.getQuestionSsno()).voteSn(voteEntity.getVoteSn())
            .qestnCn(qestn.getQuestionContents()).compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());

        QestnResponse setQestn = QestnResponse.builder().questionSsno(qestnRes.getQestnSn()).voteSsno(qestnRes.getVoteSn())
            .questionContents(qestnRes.getQestnCn()).compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt()).build();

        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteIemEntity.builder().voteSn(qestnRes.getVoteSn()).qestnSn(qestnRes.getQestnSn()).iemSn(voteIem.getItemSsno())
                .iemNm(voteIem.getItemName()).build());
          });

          // 기존에 있었던 투표항목 제거 후 등록
          voteRepository.deleteBySsnoForVoteIem(qestnRes.getVoteSn(), qestnRes.getQestnSn());

          // 등록
          List<VoteIemEntity> saveAllIemEntities = voteIemRepository.saveAll(iemEntities);
          setQestn.setVoteIemResponseList(saveAllIemEntities.stream()
              .map(entity -> VoteIemResponse.builder().itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList());
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
      log.debug("VoteService.removeVote ::::: delValQestn, delValVoteIem ::::: {}{}", delValQestn, delValVoteIem);

      if (upValVote >= 1L) {
        VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());
        return VoteResponse.builder().voteSsno(voteEntity.getVoteSn()).voteKindCode(voteEntity.getVoteKndCode()).voteSubject(voteEntity.getVoteSj())
            .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt()).voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())
            .voteDeleteAlternative(voteEntity.getVoteDeleteAt()).build();
      } else {
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

}
