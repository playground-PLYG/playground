package com.playground.api.board.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_post")
@IdClass(PostEntityPK.class)
@SequenceGenerator(
	    name = "tb_post_ntt_sn_seq",
	      sequenceName = "tb_post_ntt_sn_seq",
	      initialValue = 1,
	      allocationSize = 1
	  )
public class PostEntity extends BaseEntity{

  @Id
  @ManyToOne
  @JoinColumn(name="bbs_id")
  private NoticeEntity noticeEntity;
  
  @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tb_post_ntt_sn_seq")
	@Column(name = "ntt_sn")
	private Integer nttSn;
	
	@Column(name = "ntt_sj")
	private String nttSj;
	
	@Column(name = "ntt_cn")
	private String nttCn;
	
}
