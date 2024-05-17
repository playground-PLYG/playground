package com.playground.api.hashtag.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.hashtag.model.HashtagRequest;
import com.playground.api.hashtag.model.HashtagResponse;
import com.playground.api.hashtag.service.HashtagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "hashtag", description = "hashtag API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class HashtagController {

  private final HashtagService hashtagService;

  /**
   * 해시태그 목록 조회
   */
  @Operation(summary = "해시태그 목록 조회", description = "해시태그 목록 조회")
  @PostMapping("/public/hashtag/getHashtagList")
  public List<HashtagResponse> getHashtagList() {
    return hashtagService.getHashtagList();
  }

  /**
   * 해시태그 등록
   */
  @Operation(summary = "해시태그 상세 조회", description = "해시태그 상세 조회")
  @PostMapping("/public/hashtag/getHashtagDetail")
  public HashtagResponse getHashtagDetail(@RequestBody @Valid HashtagRequest req) {
    return hashtagService.getHashtagDetail(req);
  }

  /**
   * 해시태그 등록
   */
  @Operation(summary = "해시태그 등록", description = "해시태그 등록")
  @PostMapping("/public/hashtag/addHashtag")
  public HashtagResponse addHashtag(@RequestBody @Valid HashtagRequest req) {
    return hashtagService.addHashtag(req);
  }

  /**
   * 해시태그 등록
   */
  @Operation(summary = "해시태그 수정", description = "해시태그 수정")
  @PutMapping("/public/hashtag/modifyHashtag")
  public HashtagResponse modifyHashtag(@RequestBody @Valid HashtagRequest req) {
    return hashtagService.modifyHashtag(req);
  }

  /**
   * 해시태그 등록
   */
  @Operation(summary = "해시태그 삭제", description = "해시태그 삭제")
  @DeleteMapping("/public/hashtag/removeHashtag")
  public void removeHashtag(@RequestBody @Valid HashtagRequest req) {
    hashtagService.removeHashtag(req);
  }


}
