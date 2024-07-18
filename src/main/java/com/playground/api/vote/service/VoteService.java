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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.code.model.CodeGroupSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.service.MberService;
import com.playground.api.message.model.DiscordEmbedRequest;
import com.playground.api.message.model.DiscordRequest;
import com.playground.api.message.service.MessageService;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.service.RstrntService;
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
import com.playground.api.vote.model.VoteQestnIemRequest;
import com.playground.api.vote.model.VoteQestnIemResponse;
import com.playground.api.vote.model.VoteQestnRequest;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.model.VoteResultDetailResponse;
import com.playground.api.vote.model.VoteResultResponse;
import com.playground.api.vote.model.VoteRstrntResponse;
import com.playground.api.vote.model.VoteSrchRequest;
import com.playground.api.vote.model.VoteSrchResponse;
import com.playground.api.vote.repository.VoteAnswerRepository;
import com.playground.api.vote.repository.VoteQestnIemRepository;
import com.playground.api.vote.repository.VoteQestnRepository;
import com.playground.api.vote.repository.VoteRepository;
import com.playground.api.vote.repository.dsl.VoteRstrntRepositoryCustom;
import com.playground.exception.BizException;
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
  private final VoteRstrntRepositoryCustom voteRstrntRepositoryCustom;
  private final MessageService messageService;
  private final RstrntService rstrntService;
  private final CodeService codeService;
  private final MberService mberService;

  @Value("${CLIENT_URL}")
  private String clientUrl;
  private static final String DATETIME_1 = "yyyy-MM-dd HH:mm";

  @Transactional(readOnly = true)
  public Page<VoteSrchResponse> getVoteList(VoteSrchRequest reqData, Pageable pageable) {

    Page<VoteEntity> votePageList = voteRepository.getVotePageList(reqData, pageable);

    List<VoteSrchResponse> voteList = votePageList.getContent().stream()
        .map(voteEntity -> VoteSrchResponse.builder().voteSsno(voteEntity.getVoteSn()).voteSubject(voteEntity.getVoteSj())
            .voteBeginDate(voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
            .voteEndDate(voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1))).build())
        .toList();


    return new PageImpl<>(voteList, votePageList.getPageable(), votePageList.getTotalElements());
  }

  @Transactional
  public VoteAddResponse addVote(VoteAddRequest reqData) {

    log.debug("addVote : {}", reqData);

    LocalDateTime voteBeginDateTime = LocalDateTime.parse(reqData.getVoteBeginDate(), DateTimeFormatter.ofPattern(DATETIME_1));
    LocalDateTime voteEndDateTime = LocalDateTime.parse(reqData.getVoteEndDate(), DateTimeFormatter.ofPattern(DATETIME_1));


    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder().voteSj(reqData.getVoteSubject()).voteBeginDt(voteBeginDateTime)
        .voteEndDt(voteEndDateTime).voteExpsrAt(reqData.getVoteExposureAlternative()).voteTrnsmisAt(reqData.getVoteTransmissionAlternative())
        .voteTrnsmisCode(reqData.getVoteTransmissionCode()).build());

    VoteAddResponse voteResponse = VoteAddResponse.builder().voteSsno(voteEntity.getVoteSn()).voteSubject(voteEntity.getVoteSj())
        .voteBeginDate(voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
        .voteEndDate(voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1))).voteExposureAlternative(voteEntity.getVoteExpsrAt())
        .voteTransmissionAlternative(voteEntity.getVoteTrnsmisAt()).voteTransmissionCode(voteEntity.getVoteTrnsmisCode()).build();


    // 질문리스트 저장
    List<VoteQestnResponse> setQestnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(reqData.getVoteQestnRequestList())) {
      List<VoteQestnRequest> qestnReList = reqData.getVoteQestnRequestList();
      qestnReList.forEach(qestn -> {
        VoteQestnEntity qestnRes = voteQestnRepository
            .save(VoteQestnEntity.builder().voteSn(voteEntity.getVoteSn()).qestnCn(qestn.getQuestionContents()).voteKndCode(qestn.getVoteKndCd())
                .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).annymtyVoteAt(qestn.getAnonymityVoteAlternative()).build());

        VoteQestnResponse setQestn = VoteQestnResponse.builder().questionSsno(qestnRes.getQestnSn()).voteSsno(qestnRes.getVoteSn())
            .voteKindCode(qestnRes.getVoteKndCode()).questionContents(qestnRes.getQestnCn())
            .compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt()).anonymityVoteAlternative(qestnRes.getAnnymtyVoteAt()).build();


        // 항목리스트 저장
        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteQestnIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteQestnIemEntity.builder().voteSn(qestnRes.getVoteSn()).qestnSn(qestnRes.getQestnSn()).iemNm(voteIem.getItemName())
                .iemIdntfcId(voteIem.getItemIdentificationId()).build());
          });

          List<VoteQestnIemEntity> saveAllIemEntities = voteQestnIemRepository.saveAll(iemEntities);
          setQestn
              .setVoteQestnIemResponseList(
                  saveAllIemEntities.stream()
                      .map(entity -> VoteQestnIemResponse.builder().itemSsno(entity.getIemSn()).itemName(entity.getIemNm())
                          .voteSno(entity.getVoteSn()).questionSno(entity.getQestnSn()).itemIdentificationId(entity.getIemIdntfcId()).build())
                      .toList());
        }
        setQestnList.add(setQestn);
      });
      voteResponse.setVoteQestnResponseList(setQestnList);
    }



    // discord 메시지 전송
    DiscordRequest dto = new DiscordRequest();
    dto.setApiNm("vote"); // vote 채널의 API_KEY
    dto.setUsername(voteEntity.getRegistUsrId());
    dto.setVoteTransmissionCode(voteEntity.getVoteTrnsmisCode());

    DiscordEmbedRequest embed = new DiscordEmbedRequest();
    embed.setTitle(reqData.getVoteSubject());
    embed.setDescription("[투표하기](" + clientUrl + "/vote?ssno=" + voteEntity.getVoteSn() + ")");

    dto.addEmbed(embed);

    messageService.sendDiscordWebhook(dto);


    return voteResponse;
  }


  @Transactional
  public VoteModifyResponse modifyVote(VoteModifyRequest reqData) {

    log.debug("updateVote : {}", reqData);


    LocalDateTime voteBeginDateTime = LocalDateTime.parse(reqData.getVoteBeginDate(), DateTimeFormatter.ofPattern(DATETIME_1));
    LocalDateTime voteEndDateTime = LocalDateTime.parse(reqData.getVoteEndDate(), DateTimeFormatter.ofPattern(DATETIME_1));


    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder().voteSn(reqData.getVoteSsno()).voteSj(reqData.getVoteSubject())
        .voteBeginDt(voteBeginDateTime).voteEndDt(voteEndDateTime).voteExpsrAt(reqData.getVoteExposureAlternative())
        .voteTrnsmisAt(reqData.getVoteTransmissionAlternative()).voteTrnsmisCode(reqData.getVoteTransmissionCode()).build());

    VoteModifyResponse voteResponse = VoteModifyResponse.builder().voteSsno(voteEntity.getVoteSn()).voteSubject(voteEntity.getVoteSj())
        .voteBeginDate(voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1)))
        .voteEndDate(voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1))).voteExposureAlternative(voteEntity.getVoteExpsrAt())
        .voteTransmissionAlternative(voteEntity.getVoteTrnsmisAt()).voteTransmissionCode(voteEntity.getVoteTrnsmisCode()).build();


    if (!ObjectUtils.isEmpty(reqData.getVoteQestnRequestList())) {
      List<VoteQestnRequest> qestnReList = reqData.getVoteQestnRequestList();

      // 질문 조회
      List<VoteQestnEntity> QestnList = voteQestnRepository.findByVoteSn(voteEntity.getVoteSn());

      // 항목 조회
      QestnList.forEach(deleteQestnIem -> {
        List<VoteQestnIemEntity> qestnIem = voteQestnIemRepository.findByQestnSn(voteEntity.getVoteSn(), deleteQestnIem.getQestnSn());

        log.debug("항목삭제 !!! : {}", qestnIem);

        // 기존항목 삭제
        voteQestnIemRepository.deleteAll(qestnIem);
      });

      // 기존 질문 삭제
      voteQestnRepository.deleteAll(QestnList);


      List<VoteQestnResponse> setQestnList = new ArrayList<>();

      // 질문 등록
      qestnReList.forEach(qestn -> {
        VoteQestnEntity qestnRes = voteQestnRepository
            .save(VoteQestnEntity.builder().voteSn(voteEntity.getVoteSn()).qestnCn(qestn.getQuestionContents()).voteKndCode(qestn.getVoteKndCd())
                .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).annymtyVoteAt(qestn.getAnonymityVoteAlternative()).build());

        VoteQestnResponse setQestn = VoteQestnResponse.builder().questionSsno(qestnRes.getQestnSn()).voteSsno(qestnRes.getVoteSn())
            .voteKindCode(qestnRes.getVoteKndCode()).questionContents(qestnRes.getQestnCn())
            .compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt()).anonymityVoteAlternative(qestnRes.getAnnymtyVoteAt()).build();


        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {

          List<VoteQestnIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteQestnIemEntity.builder().voteSn(qestnRes.getVoteSn()).qestnSn(qestnRes.getQestnSn()).iemNm(voteIem.getItemName())
                .iemIdntfcId(voteIem.getItemIdentificationId()).build());
          });

          // 항목저장
          List<VoteQestnIemEntity> saveAllIemEntities = voteQestnIemRepository.saveAll(iemEntities);
          setQestn
              .setVoteQestnIemResponseList(
                  saveAllIemEntities.stream()
                      .map(entity -> VoteQestnIemResponse.builder().itemSsno(entity.getIemSn()).itemName(entity.getIemNm())
                          .questionSno(entity.getQestnSn()).voteSno(entity.getVoteSn()).itemIdentificationId(entity.getIemIdntfcId()).build())
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

      VoteResponse voteResponse = VoteResponse.builder().voteSsno(voteEntity.getVoteSn()).voteSubject(voteEntity.getVoteSj()).voteBeginDate(beginDate)
          .voteEndDate(endDate).voteExposureAlternative(voteEntity.getVoteExpsrAt()).voteTransmissionAlternative(voteEntity.getVoteTrnsmisAt())
          .voteTransmissionCode(voteEntity.getVoteTrnsmisCode()).registUserId(voteEntity.getRegistUsrId()).registDate(voteEntity.getRegistDt())
          .updateUserId(voteEntity.getUpdtUsrId()).updateDate(voteEntity.getUpdtDt()).build();

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
          VoteAnswerEntity.builder().answerUserId(req.getAnswerUserId()).voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno()).build());
      if (!ObjectUtils.isEmpty(alreadyEntityList)) {
        alreadyEntityList.forEach(entity -> voteAnswerRepository.deleteById(VoteAnswerPK.builder().answerUserId(entity.getAnswerUserId())
            .voteSn(entity.getVoteSn()).qestnSn(entity.getQestnSn()).iemSn(entity.getIemSn()).build()));
      }

      for (Integer iemSsno : req.getItemSsnoList()) {
        VoteAnswerEntity reqAnswer = VoteAnswerEntity.builder().answerUserId(req.getAnswerUserId()).voteSn(req.getVoteSsno())
            .qestnSn(req.getQuestionSsno()).iemSn(iemSsno).build();
        VoteAnswerEntity saveAnswer = voteAnswerRepository.save(reqAnswer);
        resEntityList.add(saveAnswer);
      }
    });

    return resEntityList.stream().map(entity -> VoteAnswerResponse.builder().answerUserId(entity.getAnswerUserId()).voteSsno(entity.getVoteSn())
        .questionSsno(entity.getQestnSn()).itemSsno(entity.getIemSn()).build()).toList();
  }

  @Transactional(readOnly = true)
  // (Edited by.PSJ, End date.2024.07.15)
  public VoteAnswerResponse getMyVote(VoteRequest reqData) {
    if (StringUtils.isEmpty(reqData.getAnswerUserId()) || ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      return VoteAnswerResponse.builder().isCheckAnswer(false).build();
    } else {
      List<VoteAnswerSubResponse> resEntityList = voteAnswerRepository.getMyVoteAnswerList(reqData);
      if (!ObjectUtils.isEmpty(resEntityList)) {
        return VoteAnswerResponse.builder().answerUserId(reqData.getAnswerUserId()).voteSsno(reqData.getVoteSsno()).isCheckAnswer(true)
            .voteAnswerSubList(resEntityList).build();
      } else {
        return VoteAnswerResponse.builder().isCheckAnswer(false).build();
      }
    }
  }

  @Transactional(readOnly = true)
  // (Edited by.PSJ, End date.2024.07.15)
  public VoteResponse getVoteResult(VoteRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());

      // yyyy-MM-dd HH 형태로 변경
      String beginDate = voteEntity.getVoteBeginDt().format(DateTimeFormatter.ofPattern(DATETIME_1));
      String endDate = voteEntity.getVoteEndDt().format(DateTimeFormatter.ofPattern(DATETIME_1));

      List<VoteResultResponse> voteResultList = voteAnswerRepository.getVoteQestnResult(reqData);
      Integer voteSsno = reqData.getVoteSsno();
      if (!ObjectUtils.isEmpty(voteResultList)) {
        for (VoteResultResponse reRes : voteResultList) {
          Integer questionSsno = reRes.getQuestionSsno();
          for (VoteResultDetailResponse detailReRes : reRes.getResultDetailList()) {
            List<String> userIdList = new ArrayList<>();
            userIdList = voteAnswerRepository.getAnswerUserIds(voteSsno, questionSsno, detailReRes.getItemSsno());
            detailReRes.setSelUserIdList(userIdList);
          }
        }
      }
      return VoteResponse.builder().voteSsno(voteEntity.getVoteSn()).voteSubject(voteEntity.getVoteSj()).voteBeginDate(beginDate).voteEndDate(endDate)
          .voteResultList(voteResultList).build();
    } else {
      return new VoteResponse();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  //////////////// 이하 메소드는 개발완료 후 삭제 할 예정 참고 만 하기 ////////////////////////
  /////////////////////////////////////////////////////////////////////////////


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
        return VoteResponse.builder().voteSsno(voteEntity.getVoteSn())// .voteKindCode(voteEntity.getVoteKndCode())
            .voteSubject(voteEntity.getVoteSj())// .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
            // .voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())// .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
            .build();
      } else {
        return new VoteResponse();
      }
    } else {
      return new VoteResponse();
    }
  }

  @Transactional
  public void addTodayLunchVote() {
    LocalDateTime now = LocalDateTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formatedNow = now.format(formatter);

    // 당일 투표 생성되어 있는지 검사
    Integer count = voteRstrntRepositoryCustom.getVoteRstrntCount();
    if (count > 0) {
      throw new BizException("이미 점심 투표가 생성되어 있습니다.");
    }

    // 투표 옵션을 코드로 관리하며 해당 데이터를 조회 후 설정에 맞는 투표로 생성한다.
    List<CodeSrchResponse> codeRes = codeService.getCodeGroupList(CodeGroupSrchRequest.builder().upperCode("VOTE_OPTION_CODE").build());

    String compnoYn = "N"; // 복수선택 default N
    String annymtyYn = "N"; // 익명투표 default N

    for (CodeSrchResponse res : codeRes) {
      if ("COMPNO_CHOISE_AT".equals(res.getCode())) { // 복수선택 여부
        compnoYn = res.getCodeName();
      } else if ("ANNYMTY_VOTE_AT".equals(res.getCode())) { // 익명투표 여부
        annymtyYn = res.getCodeName();
      }
    }

    // 1. tb_vote 테이블 등록
    VoteEntity resVote = voteRepository.save(VoteEntity.builder()// .voteKndCode("LUN")
        .voteSj(formatedNow + " 점심 투표")// .annymtyVoteAt(annymtyYn)
        .voteBeginDt(now).voteEndDt(now.plusDays(1))// .voteDeleteAt("N")
        .build());


    // 2. tb_qestn 테이블 등록
    VoteQestnEntity resQestn =
        voteQestnRepository.save(VoteQestnEntity.builder().voteSn(resVote.getVoteSn()).qestnCn("식당 투표 목록").compnoChoiseAt(compnoYn).build());

    // 3. tb_vote_iem 테이블 등록
    List<VoteQestnIemEntity> saveIemList = new ArrayList<>(); // 한번에 insert 하기 위한 List

    RstrntSrchRequest req = new RstrntSrchRequest();
    List<RstrntSrchResponse> resRstrntList = rstrntService.getRstrntList(req); // 식당 메뉴 조회

    for (RstrntSrchResponse res : resRstrntList) { // 조회한 식당리스트를 등록하기 위해 변환
      saveIemList.add(VoteQestnIemEntity.builder().voteSn(resVote.getVoteSn()).qestnSn(resQestn.getQestnSn()).iemSn(res.getRestaurantSerialNo()) // 이거 DB 변경해야할듯
          .iemNm(res.getRestaurantName()).build());
    }

    voteQestnIemRepository.saveAll(saveIemList);

    MberInfoResponse resMber = mberService.getMember(); // 로그인한 사용자정보
    // 최상위 웹훅 dto
    DiscordRequest dto = new DiscordRequest();
    dto.setApiNm("vote"); // vote 채널의 API_KEY
    dto.setUsername(resMber.getMberNm());
    // dto.setContent(formatedNow + " 점심 투표");
    // dto.setAvatarUrl("https://i.imgur.com/oBPXx0D.png");

    DiscordEmbedRequest embed = new DiscordEmbedRequest();
    embed.setTitle(formatedNow + " 점심 투표");
    embed.setDescription("[투표하기](" + clientUrl + "/lunch-vote)");
    embed.addField("다건투표여부", "N".equals(compnoYn) ? "단건" : "다건", true);
    embed.addField("익명투표여부", "N".equals(annymtyYn) ? "회원투표" : "익명투표", true);
    // embed.setAuthor("Author name", "test", "https://i.imgur.com/8nLFCVP.png");
    // embed.setAuthor("Author name", "https://i.imgur.com/8nLFCVP.png", "https://i.imgur.com/8nLFCVP.png");
    // embed.setFooter("■■■■■■■■■■ footer ■■■■■■■■■■", "https://i.imgur.com/Hv0xNBm.jpeg");
    // embed.setThumbnail("https://i.imgur.com/MqLKp2O.jpeg");
    // embed.setImage("https://i.imgur.com/7S5h92S.jpeg");

    dto.addEmbed(embed);

    messageService.sendDiscordWebhook(dto);

  }

  @Transactional
  public List<VoteQestnResponse> addQestn(List<VoteQestnRequest> qestnReqList) {
    List<VoteQestnEntity> resEntityList = new ArrayList<>();
    for (VoteQestnRequest qestn : qestnReqList) {
      resEntityList.add(VoteQestnEntity.builder().voteSn(qestn.getVoteSsno()).qestnCn(qestn.getQuestionContents())
          .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());
    }

    List<VoteQestnEntity> saveAllEntities = voteQestnRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteQestnResponse.builder().questionSsno(entity.getQestnSn()).voteSsno(entity.getVoteSn())
        .questionContents(entity.getQestnCn()).compoundNumberChoiceAlternative(entity.getCompnoChoiseAt()).build()).toList();
  }

  @Transactional
  public List<VoteQestnResponse> modifyQestn(List<VoteQestnRequest> qestnReqList) {
    List<VoteQestnEntity> resEntityList = new ArrayList<>();
    for (VoteQestnRequest qestn : qestnReqList) {
      VoteQestnEntity resQestn = voteQestnRepository.save(VoteQestnEntity.builder().qestnSn(qestn.getQuestionSsno()).voteSn(qestn.getVoteSsno())
          .qestnCn(qestn.getQuestionContents()).compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());

      resEntityList.add(resQestn);
    }

    return resEntityList.stream().map(entity -> VoteQestnResponse.builder().questionSsno(entity.getQestnSn()).voteSsno(entity.getVoteSn())
        .questionContents(entity.getQestnCn()).compoundNumberChoiceAlternative(entity.getCompnoChoiseAt()).build()).toList();
  }

  @Transactional
  public Long removeQestn(VoteQestnRequest qestnRequest) {
    if (!ObjectUtils.isEmpty(qestnRequest.getVoteSsno())) {
      return voteRepository.deleteByVoteSnForQestn(qestnRequest.getVoteSsno());
    } else {
      return 0L;
    }
  }

  @Transactional
  public List<VoteQestnIemResponse> addVoteIem(List<VoteQestnIemRequest> voteIemReqList) {
    List<VoteQestnIemEntity> resEntityList = new ArrayList<>();
    for (VoteQestnIemRequest voteIem : voteIemReqList) {
      resEntityList.add(VoteQestnIemEntity.builder().voteSn(voteIem.getVoteSsno()).qestnSn(voteIem.getQuestionSsno()).iemSn(voteIem.getItemSsno())
          .iemNm(voteIem.getItemName()).build());
    }

    List<VoteQestnIemEntity> saveAllEntities = voteQestnIemRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteQestnIemResponse.builder().voteSno(entity.getVoteSn()).questionSno(entity.getQestnSn())
        .itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList();
  }

  @Transactional
  public List<VoteQestnIemResponse> modifyVoteIem(List<VoteQestnIemRequest> voteIemReqList) {
    List<VoteQestnIemEntity> resEntityList = new ArrayList<>();
    for (VoteQestnIemRequest voteIem : voteIemReqList) {
      VoteQestnIemEntity resVoteIem = voteQestnIemRepository.save(VoteQestnIemEntity.builder().voteSn(voteIem.getVoteSsno())
          .qestnSn(voteIem.getQuestionSsno()).iemSn(voteIem.getItemSsno()).iemNm(voteIem.getItemName()).build());

      resEntityList.add(resVoteIem);
    }

    return resEntityList.stream().map(entity -> VoteQestnIemResponse.builder().voteSno(entity.getVoteSn()).questionSno(entity.getQestnSn())
        .itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList();
  }

  @Transactional
  public Long removeVoteIem(VoteQestnIemRequest voteIemRequest) {
    if (!ObjectUtils.isEmpty(voteIemRequest.getVoteSsno())) {
      return voteRepository.deleteByVoteSnForVoteIem(voteIemRequest.getVoteSsno());
    } else {
      return 0L;
    }
  }

  @Transactional(readOnly = true)
  public VoteResponse getVoteDetailOnAnswer(VoteRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());
      VoteResponse voteResponse = VoteResponse.builder().voteSsno(voteEntity.getVoteSn())// .voteKindCode(voteEntity.getVoteKndCode())
          .voteSubject(voteEntity.getVoteSj())// .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
          // .voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())// .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
          .build();

      List<VoteQestnResponse> qestnResponseList = voteRepository.getVoteQestnDetail(reqData.getVoteSsno());
      if (qestnResponseList.size() != 0) {
        voteResponse.setVoteQestnResponseList(qestnResponseList);
      }
      return voteResponse;
    } else {
      return new VoteResponse();
    }

  }

  @Transactional(readOnly = true)
  public List<VoteAnswerResponse> getAnswer(VoteAnswerRequest reqData) {
    log.debug("AnswerService.getAnswer ::: request ::: {}", reqData);

    // 임시 조치
    return new ArrayList<VoteAnswerResponse>();
  }


  public List<VoteRstrntResponse> getVoteRstrntList() {
    return voteRstrntRepositoryCustom.getVoteRstrntList();
  }

  private LocalDateTime stringToLocalDateTime(String strDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
    return dateTime;
  }

}
