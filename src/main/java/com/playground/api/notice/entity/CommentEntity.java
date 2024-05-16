package com.playground.api.notice.entity;

import com.playground.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "tb_comment")
@IdClass(CommentEntityPK.class)
@SequenceGenerator(
	    name = "cmnt_no_seq",
	      sequenceName = "tb_comment_cmnt_no_seq",
	      initialValue = 1,
	      allocationSize = 1
	  )
public class CommentEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cmnt_no_seq")
	@Column(name = "cmnt_no")
	private int cmntNo;
	
	@Id
	@Column(name = "ntt_no")
	private int nttNo;
	
	@Id
	@Column(name = "bbs_id")
	private String bbsId;
	
	@Column(name = "cmnt_cn")
	private String cmntCn;
	
	@Column(name = "upper_cmnt_no")
	private int upperCmntNo;
	
	
}
