package com.playground.api.vote.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteIemEntity;
import com.playground.api.vote.model.VoteRstrntResponse;
import com.playground.api.vote.repository.QestnRepository;
import com.playground.api.vote.repository.VoteIemRepository;
import com.playground.api.vote.repository.VoteRepository;
import com.playground.api.vote.repository.dsl.VoteRstrntRepositoryCustom;
import com.playground.exception.BizException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class VoteRstrntService {
  private final VoteRepository voteRepository;
  private final QestnRepository qestnRepository;
  private final VoteIemRepository voteIemRepository;
  private final RstrntService rstrntService;
  private final VoteRstrntRepositoryCustom voteRstrntRepositoryCustom;
  private final CodeService codeService;
  private final MessageService messageService;
  private final MberService mberService;

  @Value("${CLIENT_URL}")
  private String clientUrl;

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
    VoteEntity resVote = voteRepository.save(VoteEntity.builder().voteKndCode("LUN").voteSj(formatedNow + " 점심 투표").annymtyVoteAt(annymtyYn)
        .voteBeginDt(now).voteEndDt(now.plusDays(1)).voteDeleteAt("N").build());


    // 2. tb_qestn 테이블 등록
    QestnEntity resQestn =
        qestnRepository.save(QestnEntity.builder().voteSn(resVote.getVoteSn()).qestnCn("식당 투표 목록").compnoChoiseAt(compnoYn).build());

    // 3. tb_vote_iem 테이블 등록
    List<VoteIemEntity> saveIemList = new ArrayList<>(); // 한번에 insert 하기 위한 List

    RstrntSrchRequest req = new RstrntSrchRequest();
    List<RstrntSrchResponse> resRstrntList = rstrntService.getRstrntList(req); // 식당 메뉴 조회

    for (RstrntSrchResponse res : resRstrntList) { // 조회한 식당리스트를 등록하기 위해 변환
      saveIemList.add(VoteIemEntity.builder().voteSn(resVote.getVoteSn()).qestnSn(resQestn.getQestnSn()).iemSn(res.getRestaurantSerialNo()) // 이거 DB 변경해야할듯
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

  public List<VoteRstrntResponse> getVoteRstrntList() {
    return voteRstrntRepositoryCustom.getVoteRstrntList();
  }
}
