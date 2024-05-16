package com.playground.api.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playground.api.notice.entity.NoticeEntity;
import com.playground.api.notice.model.NoticeRequest;
import com.playground.api.notice.model.NoticeResponse;
import com.playground.api.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
	// private final NoticeEntity noticeEntity;
	private final NoticeRepository noticeRepository;

	/** 전체 게시판 목록 조회 */
	public List<NoticeResponse> getNoticeList() {
		List<NoticeEntity> noticeEntity = noticeRepository.findAll();
		return noticeEntity.stream().map(entity -> NoticeResponse.builder()
				.boardId(entity.getBbsId())
				.boardNm(entity.getBbsNm())
		        .build())
				.toList();
	}

	/** 게시판 생성 */
	public NoticeResponse addNotice(NoticeRequest noticeRequest) {
		NoticeEntity noticeEntity = NoticeEntity.builder()
				.bbsId(noticeRequest.getBoardId())
				.bbsNm(noticeRequest.getBoardNm())
				.build();
		noticeRepository.save(noticeEntity);
		return NoticeResponse.builder()
				.boardId(noticeEntity.getBbsId())
				.boardNm(noticeEntity.getBbsNm())
				.build();
	}
	
	/** 게시판 삭제*/
	public NoticeResponse removeNotice(NoticeRequest noticeRequest) {
		noticeRepository.deleteById(noticeRequest.getBoardId());
		return NoticeResponse.builder()
				.boardId(noticeRequest.getBoardId())
				.boardNm(noticeRequest.getBoardNm())
				.build();
	}
	
}
