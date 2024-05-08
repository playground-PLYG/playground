package com.playground.api.notice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.api.notice.model.NoticeRequest;
import com.playground.api.notice.model.NoticeResponse;
import com.playground.api.notice.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "notice", description = "게시판")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class NoticeController {
	private final NoticeService noticeService;

	/**
	 * 게시판 목록 조회
	 *
	 */
	@Operation(summary = "게시판 목록 조회", description = "게시판 목록 조회")
	@GetMapping("/public/notice/getNoticeList")
	public List<NoticeResponse> getNoticeList() {
		return noticeService.getNoticeList();
	}
	
	/**
	 * 게시판 생성
	 *
	 */
	@Operation(summary = "게시판 생성", description = "게시판 생성")
	@PostMapping("/api/notice/addNotice")
	public NoticeResponse addNotice(@RequestBody NoticeRequest noticeRequest) {
		return noticeService.addNotice(noticeRequest);
	}
	
	/**
	 * 게시판 삭제
	 *
	 */
	@Operation(summary = "게시판 삭제", description = "게시판 삭제")
	@DeleteMapping("/api/notice/removeNotice")
	public NoticeResponse removeNotice(@RequestBody NoticeRequest noticeRequest) {
		return noticeService.removeNotice(noticeRequest);
	}
	
}
