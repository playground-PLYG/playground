package com.playground.api.post.entity;

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
@Table(name = "tb_post")
public class PostEntity extends BaseEntity{

	@Id
	@Column(name = "ntt_no")
	private int nttNo;
	
	@Column(name = "bbs_id")
	private String bbsId;
	
	@Column(name = "ntt_sj")
	private String nttSj;
	
	@Column(name = "ntt_cn")
	private String nttCn;
	

	@Column(name = "regist_usr_id")
	private String registUsrId;
	
	@Column(name = "updt_usr_id")
	private String updtUsrId;
	
	
}
