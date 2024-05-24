package com.playground.api.sample.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.message.model.DiscordRequest;
import com.playground.api.message.service.MessageService;
import com.playground.api.sample.model.GroupByResponse;
import com.playground.api.sample.model.JoinResponse;
import com.playground.api.sample.model.SampleUserResponse;
import com.playground.api.sample.model.SampleUserSearchRequest;
import com.playground.api.sample.model.SmpleDetailDetailRequest;
import com.playground.api.sample.model.SmpleDetailDetailResponse;
import com.playground.api.sample.model.SmpleDetailRequest;
import com.playground.api.sample.model.SmpleDetailResponse;
import com.playground.api.sample.model.SmpleRequest;
import com.playground.api.sample.model.SmpleResponse;
import com.playground.api.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "sample", description = "샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class SampleController {
  private final SampleService sampleService;
  
  private final MessageService messageService;

  /**
   * 샘플 회원 목록 조회
   */
  @Operation(summary = "샘플 회원 목록 조회", description = "샘플 회원 목록 조회")
  @PostMapping("/public/sample/users")
  public Page<SampleUserResponse> getSampleUserPageList(@RequestBody @Valid SampleUserSearchRequest reqData, Pageable pageable) {
    return sampleService.getUserPageList(reqData, pageable);
  }

  /**
   * 샘플 목록 조회
   *
   * @return List<SmpleResponse> - 조회한 샘플 목록
   */
  @Operation(summary = "샘플 목록 조회", description = "샘플 테이블 정보 전체 조회")
  @GetMapping("/public/sample/getSmpleList")
  public Page<SmpleResponse> getSmpleList(Pageable pageable) {
    return sampleService.getSmpleList(pageable);
  }

  /**
   * 샘플 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleResponse - 조회한 샘플 단건
   */
  @Operation(summary = "샘플 단건 조회", description = "샘플 테이블 정보 단건 조회")
  @PostMapping("/public/sample/getSmpleDetail")
  public SmpleResponse getSmpleDetail(@RequestBody @Valid SmpleRequest reqData) {
    return sampleService.getSmpleDetail(reqData);
  }

  /**
   * 샘플 상세 목록 조회
   *
   * @param reqData - 조회 조건
   * @return List<SmpleDetailResponse> - 조회한 샘플 상세 목록
   */
  @Operation(summary = "샘플 상세 목록 조회", description = "샘플 상세 테이블 정보 목록 조회")
  @PostMapping("/public/sample/getSmpleDetailList")
  public List<SmpleDetailResponse> getSmpleDetailList(@RequestBody @Valid SmpleDetailRequest reqData) {
    return sampleService.getSmpleDetailList(reqData);
  }

  /**
   * 샘플 상세 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleDetailResponse - 조회한 샘플 상세 단건
   */
  @Operation(summary = "샘플 상세 단건 조회", description = "샘플 상세 테이블 정보 단건 조회")
  @PostMapping("/public/sample/getSmpleDetailDetail")
  public SmpleDetailResponse getSmpleDetailDetail(@RequestBody @Valid SmpleDetailRequest reqData) {
    return sampleService.getSmpleDetailDetail(reqData);
  }

  /**
   * 샘플 상세 상세 목록 조회
   *
   * @param reqData - 조회 조건
   * @return List<SmpleDetailDetailResponse> - 조회한 샘플 상세 상세 목록
   */
  @Operation(summary = "샘플 상세 상세 목록 조회", description = "샘플 상세 상세 테이블 정보 목록 조회")
  @PostMapping("/public/sample/getSmpleDetailDetailList")
  public List<SmpleDetailDetailResponse> getSmpleDetailDetailList(@RequestBody @Valid SmpleDetailDetailRequest reqData) {
    return sampleService.getSmpleDetailDetailList(reqData);
  }

  /**
   * 샘플 상세 상세 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleDetailDetailResponse - 조회한 샘플 상세 상세 단건
   */
  @Operation(summary = "샘플 상세 상세 단건 조회", description = "샘플 상세 상세 테이블 정보 단건 조회")
  @PostMapping("/public/sample/getSmpleDetailDetailDetail")
  public SmpleDetailDetailResponse getSmpleDetailDetailDetail(@RequestBody @Valid SmpleDetailDetailRequest reqData) {
    return sampleService.getSmpleDetailDetailDetail(reqData);
  }


  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 단건
   */
  @Operation(summary = "샘플 QueryDsl 단건 조회", description = "샘플 테이블 정보를 단건 조회")
  @PostMapping("/public/sample/getSmpleDsl")
  public SmpleResponse getSmpleDsl(@RequestBody SmpleRequest req) {
    return sampleService.getSmpleDsl(req);
  }

  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 다건
   */
  @Operation(summary = "샘플 QueryDsl 다건 조회", description = "샘플 테이블 정보를 다건 조회")
  @PostMapping("/public/sample/getSmpleDslList")
  public List<SmpleResponse> getSmpleDslList(@RequestBody SmpleRequest req) {
    return sampleService.getSmpleDslList(req);
  }

  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 페이징
   */
  @Operation(summary = "샘플 QueryDsl 단건 조회", description = "샘플 테이블 정보를 단건 조회")
  @PostMapping("/public/sample/getSmpleDslPageList")
  public Page<SmpleResponse> getSmpleDslPageList(@RequestBody SmpleRequest req, Pageable pageable) {
    return sampleService.getSmpleDslPageList(req, pageable);
  }

  /**
   * 샘플 QueryDsl 실행 테이블 두개 조회
   *
   * @return SmpleResponse - 조회한 샘플 페이징
   */
  @Operation(summary = "샘플 QueryDsl 테이블 두개 조회", description = "샘플 테이블 정보 두개 조인 조회")
  @PostMapping("/public/sample/getSmpleDslJoinList")
  public List<JoinResponse> getSmpleDslJoinList(@RequestBody SmpleRequest req) {
    return sampleService.getSmpleDslJoinList(req);
  }

  /**
   * 샘플 QueryDsl 실행 테이블 두개 조회
   *
   * @return SmpleResponse - 조회한 샘플 페이징
   */
  @Operation(summary = "샘플 QueryDsl Group by 조회", description = "샘플 테이블 정보 두개 group by 조회")
  @PostMapping("/public/sample/getSmpleDslGroupbyList")
  public List<GroupByResponse> getSmpleDslGroupbyList(@RequestBody SmpleRequest req) {
    return sampleService.getSmpleDslGroupbyList(req);
  }

  /**
   * 샘플 Detail 테이블 insert
   *
   * @return SmpleDetailResponse - 샘플 디테일
   */
  @Operation(summary = "샘플 SmpleDetail Add", description = "샘플 Detail 테이블 저장")
  @PostMapping("/public/sample/addSmpleDetail")
  public SmpleDetailResponse addSmpleDetail(@RequestBody SmpleDetailRequest req) {
    return sampleService.addSmpleDetail(req);
  }

  /**
   * 샘플 Detail 테이블 update
   *
   * @return SmpleDetailResponse - 샘플 디테일 수정
   */
  @Operation(summary = "샘플 SmpleDetail modify", description = "샘플 Detail 테이블 수정")
  @PostMapping("/public/sample/modifySmpleDetail")
  public SmpleDetailResponse modifySmpleDetail(@RequestBody SmpleDetailRequest req) {
    return sampleService.modifySmpleDetail(req);
  }

  /**
   * 샘플 Detail 테이블 delete
   *
   * @return SmpleDetailResponse - 샘플 디테일 삭제
   */
  @Operation(summary = "샘플 SmpleDetail remove", description = "샘플 Detail 테이블 삭제")
  @PostMapping("/public/sample/removeSmpleDetail")
  public void removeSmpleDetail(@RequestBody SmpleDetailRequest req) {
    sampleService.removeSmpleDetail(req);
  }

  /**
   * 샘플 Detail 테이블 insert
   *
   * @return SmpleDetailResponse - 샘플 디테일
   */
  @Operation(summary = "샘플 SmpleDetailDetail Add", description = "샘플 DetailDetail 테이블 저장")
  @PostMapping("/public/sample/addSmpleDetailDetail")
  public SmpleDetailDetailResponse addSmpleDetailDetail(@RequestBody SmpleDetailDetailRequest req) {
    return sampleService.addSmpleDetailDetail(req);
  }

  /**
   * 샘플 Detail 테이블 update
   *
   * @return SmpleDetailResponse - 샘플 디테일 수정
   */
  @Operation(summary = "샘플 SmpleDetailDetail modify", description = "샘플 DetailDetail 테이블 수정")
  @PostMapping("/public/sample/modifySmpleDetailDetail")
  public SmpleDetailDetailResponse modifySmpleDetailDetail(@RequestBody SmpleDetailDetailRequest req) {
    return sampleService.modifySmpleDetailDetail(req);
  }

  // 추후 로그인한 사용자만 가능하므로 public -> api로 변경
  @PostMapping("/public/sample/discord")
  public void discordTest() {

    /*
     * 샘플로 한번 날려보고 필요한 것만 발췌해서 쓰도록
     */

    // 최상위 웹훅 dto
    DiscordRequest dto = new DiscordRequest();
    dto.setUsername("박준원");
    dto.setContent("투표시작 테스트");
    dto.setAvatarUrl("https://i.imgur.com/oBPXx0D.png");

    DiscordRequest.Embed embed = new DiscordRequest.Embed();
    embed.setTitle("embed Title");
    embed.setDescription("embed Description [여기에 투표주소 들어감](https://www.naver.com)");
    embed.addField("field1", "field1 value", true);
    embed.addField("field2", "field2 value", true);
    embed.setAuthor("Author name", "https://i.imgur.com/8nLFCVP.png", "https://i.imgur.com/8nLFCVP.png");
    embed.setFooter("■■■■■■■■■■ footer ■■■■■■■■■■", "https://i.imgur.com/Hv0xNBm.jpeg");
    embed.setThumbnail("https://i.imgur.com/MqLKp2O.jpeg");
    embed.setImage("https://i.imgur.com/7S5h92S.jpeg");

    dto.addEmbed(embed);

    messageService.sendDiscordWebhook(dto);
  }


}


