package com.playground.api.notice.entity;

import com.playground.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "tb_notice_board")
public class NoticeEntity extends BaseEntity{
	
	@Id
	@Column(name = "bbs_id")
	private String bbsId;
	
	@Column(name = "bbs_nm")
	private String bbsNm;
	
}
