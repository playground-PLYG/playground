package com.playground.api.author.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.author.model.AuthorMenuRequest;
import com.playground.api.author.model.AuthorMenuResponse;
import com.playground.api.author.model.AuthorRequest;
import com.playground.api.author.model.AuthorResponse;
import com.playground.api.author.model.MberAuthorRequest;
import com.playground.api.author.model.MberAuthorResponse;
import com.playground.api.author.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "author", description = "author API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class AuthorController {

  private final AuthorService authorService;

  /**
   * 권한 목록 조회
   */
  @Operation(summary = "권한 목록 조회", description = "권한 목록 조회")
  @PostMapping("/public/author/getAuthorList")
  public List<AuthorResponse> getAuthorList(@RequestBody @Valid AuthorRequest req) {
    return authorService.getAuthorList(req);
  }

  /**
   * 권한 등록
   */
  @Operation(summary = "권한 등록", description = "권한 등록")
  @PostMapping("/public/author/addAuthor")
  public AuthorResponse addAuthor(@RequestBody @Valid AuthorRequest req) {
    return authorService.addAuthor(req);
  }

  /**
   * 권한 수정
   */
  @Operation(summary = "권한 수정", description = "권한 수정")
  @PostMapping("/public/author/modifyAuthor")
  public AuthorResponse modifyAuthor(@RequestBody @Valid AuthorRequest req) {
    return authorService.modifyAuthor(req);
  }

  /**
   * 권한 삭제
   */
  @Operation(summary = "권한 삭제", description = "권한 삭제")
  @PostMapping("/public/author/removeAuthor")
  public AuthorResponse removeAuthor(@RequestBody @Valid AuthorRequest req) {
    return authorService.removeAuthor(req);
  }

  /**
   * 회원별 권한 매핑목록 조회
   */
  @Operation(summary = "회원별 권한 매핑목록 조회", description = "회원별 권한 매핑목록 조회")
  @PostMapping("/public/author/getMberAuthorList")
  public List<MberAuthorResponse> getMberAuthorList(@RequestBody @Valid MberAuthorRequest req) {
    return authorService.getMberAuthorList(req);
  }

  /**
   * 회원별 권한 매핑 등록
   */
  @Operation(summary = "회원별 권한 매핑 등록", description = "회원별 권한 매핑 등록")
  @PostMapping("/public/author/addMberAuthor")
  public MberAuthorResponse addMberAuthor(@RequestBody @Valid MberAuthorRequest req) {
    return authorService.addMberAuthor(req);
  }

  /**
   * 회원별 권한 매핑 삭제
   */
  @Operation(summary = "회원별 권한 매핑 등록", description = "회원별 권한 매핑 등록")
  @PostMapping("/public/author/removeMberAuthor")
  public void removeMberAuthor(@RequestBody @Valid List<MberAuthorRequest> req) {
    authorService.removeMberAuthor(req);
  }

  /**
   * 권한별 메뉴 매핑목록 조회
   */
  @Operation(summary = "권한별 메뉴 매핑목록 조회", description = "권한별 메뉴 매핑 조회")
  @PostMapping("/public/author/getAuthorMenuList")
  public List<AuthorMenuResponse> getAuthorMenuList(@RequestBody @Valid AuthorMenuRequest req) {
    return authorService.getAuthorMenuList(req);
  }

  /**
   * 권한별 메뉴 매핑 등록
   */
  @Operation(summary = "회원별 권한 매핑 등록", description = "권한별 메뉴 매핑 등록")
  @PostMapping("/public/author/addAuthorMenu")
  public AuthorMenuResponse addAuthorMenu(@RequestBody @Valid AuthorMenuRequest req) {
    return authorService.addAuthorMenu(req);
  }

  /**
   * 권한별 메뉴 매핑 삭제
   */
  @Operation(summary = "회원별 권한 매핑 등록", description = "권한별 메뉴 매핑 등록")
  @PostMapping("/public/author/removeAuthorMenu")
  public void removeAuthorMenu(@RequestBody @Valid List<AuthorMenuRequest> req) {
    authorService.removeAuthorMenu(req);
  }

}
