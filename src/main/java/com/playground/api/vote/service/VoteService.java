package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
import com.playground.api.vote.model.StatisticsDetailDetailResponse;
import com.playground.api.vote.model.StatisticsDetailResponse;
import com.playground.api.vote.model.StatisticsRequest;
import com.playground.api.vote.model.StatisticsResponse;
import com.playground.api.vote.model.VoteAnswerRequest;
import com.playground.api.vote.model.VoteAnswerResponse;
import com.playground.api.vote.model.VoteQestnIemRequest;
import com.playground.api.vote.model.VoteQestnIemResponse;
import com.playground.api.vote.model.VoteQestnRequest;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.model.VoteRstrntResponse;
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
  private final VoteQestnRepository qestnRepository;
  private final VoteQestnIemRepository voteIemRepository;
  private final VoteAnswerRepository voteAnswerRepository;
  private final VoteRstrntRepositoryCustom voteRstrntRepositoryCustom;

  private final ModelMapper modelMapper;
  private final MessageService messageService;
  private final RstrntService rstrntService;
  private final CodeService codeService;
  private final MberService mberService;

  @Value("${CLIENT_URL}")
  private String clientUrl;

  @Transactional(readOnly = true)
  public Page<VoteResponse> getVoteList(VoteRequest reqData, Pageable pageable) {
    Page<VoteEntity> votePageList = voteRepository.getVotePageList(reqData, pageable);

    // 임시조치
    List<VoteResponse> voteList = new ArrayList<>();
    return new PageImpl<>(voteList, votePageList.getPageable(), votePageList.getTotalElements());
  }

  @Transactional
  public VoteResponse addVote(VoteRequest reqData) {
    log.debug("VoteService.addVote ::::: voteRequestData ::::: {}", reqData);
    VoteResponse voteResponse = new VoteResponse();

    // builder 대신 modelMapper 사용해보기
    modelMapper.typeMap(VoteEntity.class, VoteResponse.class).addMappings(mapper -> {
      mapper.map(VoteEntity::getVoteSn, VoteResponse::setVoteSsno);
      mapper.map(VoteEntity::getVoteSj, VoteResponse::setVoteSubject);
      mapper.map(VoteEntity::getVoteBeginDt, VoteResponse::setVoteBeginDate);
      mapper.map(VoteEntity::getVoteEndDt, VoteResponse::setVoteEndDate);
    });

    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder().voteSj(reqData.getVoteSubject()) // .voteKndCode(reqData.getVoteKindCode())
        .voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate())) // .annymtyVoteAt(reqData.getAnonymityVoteAlternative())
        .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate())) // .voteDeleteAt(reqData.getVoteDeleteAlternative())
        .build());

    voteResponse = modelMapper.map(voteEntity, VoteResponse.class);

    List<VoteQestnResponse> setQestnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
      List<VoteQestnRequest> qestnReList = reqData.getQestnRequestList();
      qestnReList.forEach(qestn -> {
        VoteQestnEntity qestnRes = qestnRepository.save(VoteQestnEntity.builder().voteSn(voteEntity.getVoteSn()).qestnCn(qestn.getQuestionContents())
            .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());

        VoteQestnResponse setQestn = VoteQestnResponse.builder().questionSsno(qestnRes.getQestnSn()).voteSsno(qestnRes.getVoteSn())
            .questionContents(qestnRes.getQestnCn()).compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt()).build();

        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteQestnIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteQestnIemEntity.builder().voteSn(qestnRes.getVoteSn()).qestnSn(qestnRes.getQestnSn()).iemSn(voteIem.getItemSsno())
                .iemNm(voteIem.getItemName()).build());
          });

          List<VoteQestnIemEntity> saveAllIemEntities = voteIemRepository.saveAll(iemEntities);
          setQestn.setVoteIemResponseList(saveAllIemEntities.stream()
              .map(entity -> VoteQestnIemResponse.builder().itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList());

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

  @Transactional(readOnly = true)
  public VoteResponse getVoteDetail(VoteRequest reqData) {
    log.debug("VoteService.getVoteDetail ::::: voteRequest ::::: {}", reqData);
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());

      modelMapper.typeMap(VoteEntity.class, VoteResponse.class).addMappings(mapper -> {
        mapper.map(VoteEntity::getVoteSn, VoteResponse::setVoteSsno);
        mapper.map(VoteEntity::getVoteSj, VoteResponse::setVoteSubject);
        mapper.map(VoteEntity::getVoteBeginDt, VoteResponse::setVoteBeginDate);
        mapper.map(VoteEntity::getVoteEndDt, VoteResponse::setVoteEndDate);
      });

      VoteResponse voteResponse = modelMapper.map(voteEntity, VoteResponse.class);

      List<VoteQestnResponse> qestnResponseList = voteRepository.getQestnDetail(reqData.getVoteSsno(), reqData.getQuestionSsno());
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
  public List<VoteAnswerResponse> addVoteAnswer(List<VoteAnswerRequest> reqDataList) {
    List<VoteAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      resEntityList.add(VoteAnswerEntity.builder().voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno()).iemSn(req.getItemSsno())
          // .answerUserId(StringUtils.defaultString(req.getAnswerUserId())).answerCn(StringUtils.defaultString(req.getAnswerContents()))
          .build());
    });

    // 임시 조치
    return new ArrayList<VoteAnswerResponse>();
  }



  /////////////////////////////////////////////////////////////////////////////
  //////////////// 이하 메소드는 개발완료 후 삭제 할 예정 참고 만 하기 ////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  @Transactional
  public VoteResponse modifyVote(VoteRequest reqData) {
    log.debug("VoteService.modifyVote ::::: voteRequestData ::::: {}", reqData);

    VoteEntity voteEntity = voteRepository.save(VoteEntity.builder().voteSn(reqData.getVoteSsno()).voteSj(reqData.getVoteSubject()) // .voteKndCode(reqData.getVoteKindCode())
        .voteBeginDt(this.stringToLocalDateTime(reqData.getVoteBeginDate())) // .annymtyVoteAt(reqData.getAnonymityVoteAlternative())
        .voteEndDt(this.stringToLocalDateTime(reqData.getVoteEndDate()))// .voteDeleteAt(reqData.getVoteDeleteAlternative())
        .build());

    VoteResponse resData = VoteResponse.builder().voteSsno(voteEntity.getVoteSn())// .voteKindCode(voteEntity.getVoteKndCode())
        .voteSubject(voteEntity.getVoteSj())// .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
        .voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())// .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
        .build();

    // voteEntity 에서 기존에 있었던 questionSsno 랑 reqData 로 들어온 queestionSsno랑 비교해서
    List<VoteQestnEntity> prevQestnList = voteRepository.getQestnList(reqData.getVoteSsno());
    List<Integer> prevQestnSnList = new ArrayList<>();
    List<Integer> afterQestnSnList = new ArrayList<>();
    List<Integer> removeQestnSnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(prevQestnList)) {
      for (VoteQestnEntity prev : prevQestnList) {
        prevQestnSnList.add(prev.getQestnSn());
      }

      if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
        for (VoteQestnRequest after : reqData.getQestnRequestList()) {
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

    List<VoteQestnResponse> setQestnList = new ArrayList<>();
    if (!ObjectUtils.isEmpty(reqData.getQestnRequestList())) {
      List<VoteQestnRequest> qestnReList = reqData.getQestnRequestList();
      qestnReList.forEach(qestn -> {
        VoteQestnEntity qestnRes = qestnRepository.save(VoteQestnEntity.builder().qestnSn(qestn.getQuestionSsno()).voteSn(voteEntity.getVoteSn())
            .qestnCn(qestn.getQuestionContents()).compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());

        VoteQestnResponse setQestn = VoteQestnResponse.builder().questionSsno(qestnRes.getQestnSn()).voteSsno(qestnRes.getVoteSn())
            .questionContents(qestnRes.getQestnCn()).compoundNumberChoiceAlternative(qestnRes.getCompnoChoiseAt()).build();

        if (!ObjectUtils.isEmpty(qestn.getVoteIemRequestList())) {
          List<VoteQestnIemEntity> iemEntities = new ArrayList<>();
          qestn.getVoteIemRequestList().forEach(voteIem -> {
            iemEntities.add(VoteQestnIemEntity.builder().voteSn(qestnRes.getVoteSn()).qestnSn(qestnRes.getQestnSn()).iemSn(voteIem.getItemSsno())
                .iemNm(voteIem.getItemName()).build());
          });

          // 기존에 있었던 투표항목 제거 후 등록
          voteRepository.deleteBySsnoForVoteIem(qestnRes.getVoteSn(), qestnRes.getQestnSn());

          // 등록
          List<VoteQestnIemEntity> saveAllIemEntities = voteIemRepository.saveAll(iemEntities);
          setQestn.setVoteIemResponseList(saveAllIemEntities.stream()
              .map(entity -> VoteQestnIemResponse.builder().itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList());
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
        return VoteResponse.builder().voteSsno(voteEntity.getVoteSn())// .voteKindCode(voteEntity.getVoteKndCode())
            .voteSubject(voteEntity.getVoteSj())// .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
            .voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())// .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
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
        qestnRepository.save(VoteQestnEntity.builder().voteSn(resVote.getVoteSn()).qestnCn("식당 투표 목록").compnoChoiseAt(compnoYn).build());

    // 3. tb_vote_iem 테이블 등록
    List<VoteQestnIemEntity> saveIemList = new ArrayList<>(); // 한번에 insert 하기 위한 List

    RstrntSrchRequest req = new RstrntSrchRequest();
    List<RstrntSrchResponse> resRstrntList = rstrntService.getRstrntList(req); // 식당 메뉴 조회

    for (RstrntSrchResponse res : resRstrntList) { // 조회한 식당리스트를 등록하기 위해 변환
      saveIemList.add(VoteQestnIemEntity.builder().voteSn(resVote.getVoteSn()).qestnSn(resQestn.getQestnSn()).iemSn(res.getRestaurantSerialNo()) // 이거 DB 변경해야할듯
          .iemNm(res.getRestaurantName()).build());
    }

    voteIemRepository.saveAll(saveIemList);

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

    List<VoteQestnEntity> saveAllEntities = qestnRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteQestnResponse.builder().questionSsno(entity.getQestnSn()).voteSsno(entity.getVoteSn())
        .questionContents(entity.getQestnCn()).compoundNumberChoiceAlternative(entity.getCompnoChoiseAt()).build()).toList();
  }

  @Transactional
  public List<VoteQestnResponse> modifyQestn(List<VoteQestnRequest> qestnReqList) {
    List<VoteQestnEntity> resEntityList = new ArrayList<>();
    for (VoteQestnRequest qestn : qestnReqList) {
      VoteQestnEntity resQestn = qestnRepository.save(VoteQestnEntity.builder().qestnSn(qestn.getQuestionSsno()).voteSn(qestn.getVoteSsno())
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

    List<VoteQestnIemEntity> saveAllEntities = voteIemRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteQestnIemResponse.builder().voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
        .itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList();
  }

  @Transactional
  public List<VoteQestnIemResponse> modifyVoteIem(List<VoteQestnIemRequest> voteIemReqList) {
    List<VoteQestnIemEntity> resEntityList = new ArrayList<>();
    for (VoteQestnIemRequest voteIem : voteIemReqList) {
      VoteQestnIemEntity resVoteIem = voteIemRepository.save(VoteQestnIemEntity.builder().voteSn(voteIem.getVoteSsno())
          .qestnSn(voteIem.getQuestionSsno()).iemSn(voteIem.getItemSsno()).iemNm(voteIem.getItemName()).build());

      resEntityList.add(resVoteIem);
    }

    return resEntityList.stream().map(entity -> VoteQestnIemResponse.builder().voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
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
  public Boolean isDuplicateVote(VoteAnswerRequest reqData) {
    Boolean isDuplicate = true; // true = 중복투표, false = 처음투표
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno()) && !StringUtils.isEmpty(reqData.getAnswerUserId())) {
      Long resultCount = voteAnswerRepository.selectByAnswerUserId(reqData.getVoteSsno(), reqData.getAnswerUserId());
      if (resultCount.intValue() > 0) {
        isDuplicate = true; // 중복
      } else {
        isDuplicate = false; // 처음
      }
    }
    return isDuplicate;
  }

  @Transactional(readOnly = true)
  public VoteResponse getVoteDetailOnAnswer(VoteRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());
      VoteResponse voteResponse = VoteResponse.builder().voteSsno(voteEntity.getVoteSn())// .voteKindCode(voteEntity.getVoteKndCode())
          .voteSubject(voteEntity.getVoteSj())// .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
          .voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())// .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
          .build();

      List<VoteQestnResponse> qestnResponseList = voteRepository.getQestnDetail(reqData.getVoteSsno(), reqData.getQuestionSsno());
      if (qestnResponseList.size() != 0) {
        voteResponse.setQestnResponseList(qestnResponseList);
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



  @Transactional
  public List<VoteAnswerResponse> modifyAnswer(List<VoteAnswerRequest> reqDataList) {
    List<VoteAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      VoteAnswerEntity reqAnswer = VoteAnswerEntity.builder()// .answerSn(req.getAnswerSsno())
          .voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno())
          // .answerUsrId(StringUtils.defaultString(req.getAnswerUserId()))
          .iemSn(req.getItemSsno())
          // .answerCn(StringUtils.defaultString(req.getAnswerContents()))
          .build();

      VoteAnswerEntity resAnswer = voteAnswerRepository.selectByEntity(reqAnswer);

      if (!ObjectUtils.isEmpty(resAnswer)) {
        voteAnswerRepository.deleteById(VoteAnswerPK.builder()// .answerSn(resAnswer.getAnswerSn())
            .voteSn(resAnswer.getVoteSn()).qestnSn(resAnswer.getQestnSn()).iemSn(resAnswer.getIemSn()).build());
      }
      VoteAnswerEntity saveAnswer = voteAnswerRepository.save(reqAnswer);
      resEntityList.add(saveAnswer);
    });

    // 임시 조치
    return new ArrayList<VoteAnswerResponse>();
  }

  @Transactional
  public Long removeAnswer(VoteAnswerRequest qestnAnswerRequest) {
    if (!ObjectUtils.isEmpty(qestnAnswerRequest.getAnswerSsno())) {
      return voteAnswerRepository.deleteBySsno(qestnAnswerRequest.getAnswerSsno());
    } else {
      return 0L;
    }
  }

  @Transactional(readOnly = true)
  public StatisticsResponse getVoteStatistics(StatisticsRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      StatisticsResponse statResponse = voteAnswerRepository.selectVoteStatistics(reqData);
      List<StatisticsDetailResponse> detailList = voteAnswerRepository.selectVoteDetailStatistics(reqData);
      statResponse.setStaDetailList(detailList);

      Integer voteNo = statResponse.getVoteSsno();
      if (!ObjectUtils.isEmpty(detailList)) {
        for (StatisticsDetailResponse detail : detailList) {
          Integer questionNo = detail.getQuestionSsno();
          for (StatisticsDetailDetailResponse ddetail : detail.getStaDetailDetailList()) {
            List<String> userIdList = new ArrayList<>();
            userIdList = voteAnswerRepository.selectAnswerUserIds(voteNo, questionNo, ddetail.getItemSsno());
            ddetail.setSelUserIdList(userIdList);
          }
        }
      }
      return statResponse;
    } else {
      return new StatisticsResponse();
    }
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
