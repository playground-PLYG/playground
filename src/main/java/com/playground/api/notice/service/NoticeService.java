package com.playground.api.notice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
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
	private final ModelMapper modelMapper;

	/** 전체 게시판 목록 조회 */
	public List<NoticeResponse> getNoticeList() {
		List<NoticeEntity> noticeEntity = noticeRepository.findAll();
		return noticeEntity.stream().map(item -> modelMapper.map(item, NoticeResponse.class)).toList();
	}

	/** 게시판 생성 */
	public NoticeResponse addNotice(NoticeRequest noticeRequest) {
		NoticeEntity noticeEntity = NoticeEntity.builder()
				.bbsId(noticeRequest.getBbsId())
				.bbsNm(noticeRequest.getBbsNm())
				.registUsrId(noticeRequest.getRegistUsrId())
				.updtUsrId(noticeRequest.getUpdtUsrId())
				.build();
		noticeRepository.save(noticeEntity);
		return modelMapper.map(noticeEntity, NoticeResponse.class);
	}
	
	/** 게시판 삭제*/
	public NoticeResponse removeNotice(NoticeRequest noticeRequest) {
		noticeRepository.deleteById(noticeRequest.getBbsId());
		return modelMapper.map(noticeRequest, NoticeResponse.class);
	}
	
}
