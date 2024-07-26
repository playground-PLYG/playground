package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.message.model.DiscordEmbedRequest;
import com.playground.api.message.model.DiscordRequest;
import com.playground.api.message.service.MessageService;
import com.playground.api.sample.model.WebSocketDto;
import com.playground.api.vote.entity.VoteAnswerEntity;
import com.playground.api.vote.entity.VoteAnswerPK;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteQestnEntity;
import com.playground.api.vote.entity.VoteQestnIemEntity;
import com.playground.api.vote.model.VoteAddRequest;
import com.playground.api.vote.model.VoteAddResponse;
import com.playground.api.vote.model.VoteAnswerRequest;
import com.playground.api.vote.model.VoteAnswerResponse;
import com.playground.api.vote.model.VoteAnswerSubResponse;
import com.playground.api.vote.model.VoteModifyRequest;
import com.playground.api.vote.model.VoteModifyResponse;
import com.playground.api.vote.model.VoteQestnAddRequest;
import com.playground.api.vote.model.VoteQestnAddResponse;
import com.playground.api.vote.model.VoteQestnIemAddResponse;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.model.VoteResultDetailDetailResponse;
import com.playground.api.vote.model.VoteResultDetailResponse;
import com.playground.api.vote.model.VoteResultResponse;
import com.playground.api.vote.model.VoteSrchRequest;
import com.playground.api.vote.model.VoteSrchResponse;
import com.playground.api.vote.repository.VoteAnswerRepository;
import com.playground.api.vote.repository.VoteQestnIemRepository;
import com.playground.api.vote.repository.VoteQestnRepository;
import com.playground.api.vote.repository.VoteRepository;
import com.playground.constants.RedisSubscibeChannel;
import com.playground.constants.WebSocketMessageType;
import com.playground.constants.WebSocketTargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {
  private final VoteRepository voteRepository;
  private final VoteQestnRepository voteQestnRepository;
  private final VoteQestnIemRepository voteQestnIemRepository;
  private final VoteAnswerRepository voteAnswerRepository;
  private final MessageService messageService;
  private final RedisTemplate<String, Object> redisTemplate;

  @Value("${CLIENT_URL}")
  private String clientUrl;
  private static final String DATETIME_1 = "yyyy-MM-dd HH:mm";

  @Transactional(readOnly = true)
  public Page<VoteSrchResponse> getVoteList(VoteSrchRequest reqData, Pageable pageable) {

    Page<VoteSrchResponse> votePageList = voteRepository.getVotePageList(reqData, pageable);

    return new PageImpl<VoteSrchResponse>(votePageList.getContent(), votePageList.getPageable(), votePageList.getTotalElements());
  }

  @Transactional
  public VoteAddResponse addVote(VoteAddRequest reqData) {

    DiscordEmbedRequest embed = new DiscordEmbedRequest();

    LocalDateTime voteBeginDateTime = LocalDateTime.parse(reqData.getVoteBeginDate(), DateTimeFormatter.ofPattern(DATETIME_1));
    LocalDateTime voteEndDateTime = LocalDateTime.parse(reqData.getVoteEndDate(), DateTimeFormatter.ofPattern(DATETIME_1));


    VoteEntity voteEntity =
        voteRepository.save(VoteEntity.builder()
            .voteSj(reqData.getVoteSubject())
            .voteBeginDt(voteBeginDateTime)
            .voteEndDt(voteEndDateTime)
            .voteExpsrAt(reqData.getVoteExposureAlternative())
            .voteTrnsmisCode(reqData.getVoteTransmissionCode())
            .voteTrnsmisAt(reqData.getVoteTransmissionAlternative())
            .build());


    VoteAddResponse voteResponse = VoteAddResponse.builder()
        .voteSsno(voteEntity.getVoteSn())
        .voteSubject(voteEntity.getVoteSj())
        .voteBeginDate(voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
        .voteEndDate(voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
        .voteExposureAlternative(voteEntity.getVoteExpsrAt())
        .voteTransmissionCode(voteEntity.getVoteTrnsmisCode())
        .voteTransmissionAlternative(voteEntity.getVoteTrnsmisAt())
        .build();


    // 질문리스트 저장
    List<VoteQestnAddResponse> setQestnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(reqData.getVoteQestnRequestList())) {
      List<VoteQestnAddRequest> qestnReList = reqData.getVoteQestnRequestList();
      qestnReList.forEach(qestn -> {
        VoteQestnEntity qestnRes = voteQestnRepository
            .save(VoteQestnEntity.builder()
                .voteSn(voteEntity.getVoteSn())
                .qestnCn(qestn.getQuestionContents())
                .voteKndCode(qestn.getVoteKndCd())
                .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
                .annymtyVoteAt(qestn.getAnonymityVoteAlternative())
                .build());


        VoteQestnAddResponse setQestn = VoteQestnAddResponse.builder()
            .questionSsno(qestnRes.getQestnSn())
            .voteSsno(qestnRes.getVoteSn())
            .voteKindCode(qestnRes.getVoteKndCode())
            .questionContents(qestnRes.getQestnCn())
            .compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt())
            .anonymityVoteAlternative(qestnRes.getAnnymtyVoteAt())
            .build();


        // 항목리스트 저장
        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteQestnIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteQestnIemEntity.builder()
                .voteSn(qestnRes.getVoteSn())
                .qestnSn(qestnRes.getQestnSn())
                .iemNm(voteIem.getItemName())
                .iemIdntfcId(voteIem.getItemIdentificationId())
                .build());
          });

          List<VoteQestnIemEntity> saveAllIemEntities = voteQestnIemRepository.saveAll(iemEntities);
          setQestn
              .setVoteQestnIemResponseList(
                  saveAllIemEntities.stream()
                      .map(entity -> VoteQestnIemAddResponse.builder()
                          .itemSsno(entity.getIemSn())
                          .itemName(entity.getIemNm())
                          .voteSno(entity.getVoteSn())
                          .questionSno(entity.getQestnSn())
                          .itemIdentificationId(entity.getIemIdntfcId())
                          .build())
                      .toList());
        }
        setQestnList.add(setQestn);
      });
      voteResponse.setVoteQestnResponseList(setQestnList);
    }

    // discord 메시지 전송
    DiscordRequest dto = new DiscordRequest();
    dto.setApiNm("vote"); // vote 채널의 API_KEY
    dto.setUsername(reqData.getUserId());

    embed.setTitle(reqData.getVoteSubject());
    embed.setAuthor(reqData.getUserId(), "", "");
    embed.setDescription("[투표하기](" + clientUrl + "/vote-user?ssno=" + voteEntity.getVoteSn() + ")");

    dto.addEmbed(embed);

    if ("NOW".equals(reqData.getVoteTransmissionCode())) {
      messageService.sendDiscordWebhook(dto);

      String message = reqData.getVoteSubject() + " 투표가 생성됐습니다.  &nbsp;<a href='/vote-user?ssno=" + voteEntity.getVoteSn() + "'>[투표하기]</a>";
      WebSocketDto webSocketDto = WebSocketDto.builder()
          .targetType(WebSocketTargetType.ALL)
          .sendDate(LocalDateTime.now()).message(message)
          .messageType(WebSocketMessageType.HTML)
          .message(message)
          .senderId(reqData.getUserId())
          .build();

      redisTemplate.convertAndSend(RedisSubscibeChannel.WEBSOCKET_TOPIC.name(), webSocketDto);

    }

    return voteResponse;
  }


  @Transactional
  public VoteModifyResponse modifyVote(VoteModifyRequest reqData) {

    log.debug("updateVote : {}", reqData);


    LocalDateTime voteBeginDateTime = LocalDateTime.parse(reqData.getVoteBeginDate(), DateTimeFormatter.ofPattern(DATETIME_1));
    LocalDateTime voteEndDateTime = LocalDateTime.parse(reqData.getVoteEndDate(), DateTimeFormatter.ofPattern(DATETIME_1));


    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder()
        .voteSn(reqData.getVoteSsno())
        .voteSj(reqData.getVoteSubject())
        .voteBeginDt(voteBeginDateTime)
        .voteEndDt(voteEndDateTime)
        .voteExpsrAt(reqData.getVoteExposureAlternative())
        .voteTrnsmisAt(reqData.getVoteTransmissionAlternative())
        .voteTrnsmisCode(reqData.getVoteTransmissionCode())
        .build());

    VoteModifyResponse voteResponse = VoteModifyResponse.builder()
        .voteSsno(voteEntity.getVoteSn())
        .voteSubject(voteEntity.getVoteSj())
        .voteBeginDate(voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
        .voteEndDate(voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
        .voteExposureAlternative(voteEntity.getVoteExpsrAt())
        .voteTransmissionAlternative(voteEntity.getVoteTrnsmisAt())
        .voteTransmissionCode(voteEntity.getVoteTrnsmisCode())
        .build();


    if (!ObjectUtils.isEmpty(reqData.getVoteQestnRequestList())) {
      List<VoteQestnAddRequest> qestnReList = reqData.getVoteQestnRequestList();

      // 질문 조회
      List<VoteQestnEntity> QestnList = voteQestnRepository.findByVoteSn(voteEntity.getVoteSn());

      // 항목 조회
      QestnList.forEach(deleteQestnIem -> {
        List<VoteQestnIemEntity> qestnIem = voteQestnIemRepository.findByQestnSn(voteEntity.getVoteSn(), deleteQestnIem.getQestnSn());


        // 기존항목 삭제
        voteQestnIemRepository.deleteAll(qestnIem);
      });

      // 기존 질문 삭제
      voteQestnRepository.deleteAll(QestnList);


      List<VoteQestnAddResponse> setQestnList = new ArrayList<>();

      // 질문 등록
      qestnReList.forEach(qestn -> {
        VoteQestnEntity qestnRes = voteQestnRepository
            .save(VoteQestnEntity.builder()
                .voteSn(voteEntity.getVoteSn())
                .qestnCn(qestn.getQuestionContents())
                .voteKndCode(qestn.getVoteKndCd())
                .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
                .annymtyVoteAt(qestn.getAnonymityVoteAlternative())
                .build());

        VoteQestnAddResponse setQestn = VoteQestnAddResponse.builder()
            .questionSsno(qestnRes.getQestnSn())
            .voteSsno(qestnRes.getVoteSn())
            .voteKindCode(qestnRes.getVoteKndCode())
            .questionContents(qestnRes.getQestnCn())
            .compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt())
            .anonymityVoteAlternative(qestnRes.getAnnymtyVoteAt())
            .build();


        // 항목리스트 저장
        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteQestnIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteQestnIemEntity.builder()
                .voteSn(qestnRes.getVoteSn())
                .qestnSn(qestnRes.getQestnSn())
                .iemNm(voteIem.getItemName())
                .iemIdntfcId(voteIem.getItemIdentificationId())
                .build());
          });

          List<VoteQestnIemEntity> saveAllIemEntities = voteQestnIemRepository.saveAll(iemEntities);
          setQestn
              .setVoteQestnIemResponseList(
                  saveAllIemEntities.stream()
                      .map(entity -> VoteQestnIemAddResponse.builder()
                          .itemSsno(entity.getIemSn())
                          .itemName(entity.getIemNm())
                          .voteSno(entity.getVoteSn())
                          .questionSno(entity.getQestnSn())
                          .itemIdentificationId(entity.getIemIdntfcId())
                          .build())
                      .toList());
        }
        setQestnList.add(setQestn);
      });
      voteResponse.setVoteQestnResponseList(setQestnList);
    }
    return voteResponse;
  }

  @Transactional(readOnly = true)
  // (Edited by.PSJ, End date.2024.07.08)
  public VoteResponse getVoteDetail(VoteRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());

      // yyyy-MM-dd HH 형태로 변경
      String beginDate = voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1));
      String endDate = voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1));

      VoteResponse voteResponse = VoteResponse.builder()
          .voteSsno(voteEntity.getVoteSn()).voteSubject(voteEntity.getVoteSj())
          .voteBeginDate(beginDate)
          .voteEndDate(endDate).voteExposureAlternative(voteEntity.getVoteExpsrAt())
          .voteTransmissionAlternative(voteEntity.getVoteTrnsmisAt())
          .voteTransmissionCode(voteEntity.getVoteTrnsmisCode()).registUserId(voteEntity.getRegistUsrId())
          .registDate(voteEntity.getRegistDt())
          .updateUserId(voteEntity.getUpdtUsrId())
          .updateDate(voteEntity.getUpdtDt()).build();

      List<VoteQestnResponse> voteQestnResponseList = voteRepository.getVoteQestnDetail(reqData.getVoteSsno());
      if (!ObjectUtils.isEmpty(voteQestnResponseList)) {
        voteResponse.setVoteQestnResponseList(voteQestnResponseList);
      }

      return voteResponse;
    } else {
      return new VoteResponse();
    }
  }

  @Transactional
  // (Edited by.PSJ, End date.2024.07.08)
  public List<VoteAnswerResponse> addVoteAnswer(List<VoteAnswerRequest> reqDataList) {
    List<VoteAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      List<VoteAnswerEntity> alreadyEntityList = voteAnswerRepository.getVoteAnswerEntityList(
          VoteAnswerEntity.builder()
          .answerUserId(req.getAnswerUserId())
          .voteSn(req.getVoteSsno())
          .qestnSn(req.getQuestionSsno())
          .build());
      if (!ObjectUtils.isEmpty(alreadyEntityList)) {
        alreadyEntityList.forEach(entity -> voteAnswerRepository.deleteById(VoteAnswerPK.builder()
            .answerUserId(entity.getAnswerUserId())
            .voteSn(entity.getVoteSn())
            .qestnSn(entity.getQestnSn())
            .iemSn(entity.getIemSn())
            .build()));
      }

      for (Integer iemSsno : req.getItemSsnoList()) {
        VoteAnswerEntity reqAnswer = VoteAnswerEntity.builder()
            .answerUserId(req.getAnswerUserId())
            .voteSn(req.getVoteSsno())
            .qestnSn(req.getQuestionSsno())
            .iemSn(iemSsno).build();
        VoteAnswerEntity saveAnswer = voteAnswerRepository.save(reqAnswer);
        resEntityList.add(saveAnswer);
      }
    });

    return resEntityList.stream().map(entity -> VoteAnswerResponse.builder()
        .answerUserId(entity.getAnswerUserId())
        .voteSsno(entity.getVoteSn())
        .questionSsno(entity.getQestnSn())
        .itemSsno(entity.getIemSn())
        .build())
        .toList();
  }

  @Transactional(readOnly = true)
  // (Edited by.PSJ, End date.2024.07.15)
  public VoteAnswerResponse getMyVote(VoteRequest reqData) {
    if (StringUtils.isEmpty(reqData.getAnswerUserId()) || ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      return VoteAnswerResponse.builder().isCheckAnswer(false).build();
    } else {
      List<VoteAnswerSubResponse> resEntityList = voteAnswerRepository.getMyVoteAnswerList(reqData);
      if (!ObjectUtils.isEmpty(resEntityList)) {
        return VoteAnswerResponse.builder()
            .answerUserId(reqData.getAnswerUserId())
            .voteSsno(reqData.getVoteSsno())
            .isCheckAnswer(true)
            .voteAnswerSubList(resEntityList)
            .build();
      } else {
        return VoteAnswerResponse.builder().isCheckAnswer(false).build();
      }
    }
  }

  @Transactional(readOnly = true)
  // (Edited by.PSJ, End date.2024.07.15)
  public VoteResultResponse getVoteResult(VoteRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());

      // yyyy-MM-dd HH 형태로 변경
      String beginDate = voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1));
      String endDate = voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1));

      List<VoteResultDetailResponse> voteResultList = voteAnswerRepository.getVoteQestnResult(reqData);
      Integer voteSsno = reqData.getVoteSsno();
      if (!ObjectUtils.isEmpty(voteResultList)) {
        for (VoteResultDetailResponse reRes : voteResultList) {
          Integer questionSsno = reRes.getQuestionSsno();
          for (VoteResultDetailDetailResponse detailReRes : reRes.getResultDetailList()) {
            List<String> userIdList = new ArrayList<>();
            userIdList = voteAnswerRepository.getAnswerUserIds(voteSsno, questionSsno, detailReRes.getItemSsno());
            detailReRes.setSelUserIdList(userIdList);
          }
        }
      }
      return VoteResultResponse.builder()
          .voteSsno(voteEntity.getVoteSn())
          .voteSubject(voteEntity.getVoteSj())
          .voteBeginDate(beginDate)
          .voteEndDate(endDate)
          .voteResultList(voteResultList)
          .build();
    } else {
      return new VoteResultResponse();
    }
  }
}
