package com.playground.api.member.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.playground.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_member")
public class PgMemberEntity extends BaseEntity {

  /**
   * 회원번호 
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mbr_no")
  private Integer mbrNo;
	
  /**
   * 회원명
   */
  @Column(name = "mbr_nm")
  private String mbrNm;
  
  /**
   * 회원생년월일
   */
  @Column(name = "mbr_brdt")
  private String mbrBrdt;

  /**
   * 회원성별코드 
   */
  @Column(name = "mbr_gndr_cd")
  private String mbrGndrCd;

  /**
   * 회원이메일주소 
   */
  @Column(name = "mbr_eml_addr")
  private String mbrEmlAddr;

  /**
   * CI값
   */
  @Column(name = "ci_vl")
  private String ciVl;

  /**
   * DI값
   */
  @Column(name = "di_vl")
  private String diVl;
  
  /**
   * 회원전화번호 
   */
  @Column(name = "mbr_telno")
  private String mbrTelno;

  /**
   * 등록회원번호
   */
  @Column(name = "reg_mbr_no")
  private Integer regMbrNo;

  /**
   * 등록일시
   */
  @CreationTimestamp
  @Column(name = "reg_dt", insertable = true, updatable = false)
  private LocalDateTime regDt;

  /**
   * 수정회원번호
   */
  @Column(name = "mdfcn_mbr_no")
  private Integer mdfcnMbrNo;

  /**
   * 수정일시 
   */
  @UpdateTimestamp
  @Column(name = "mdfcn_dt", insertable = false, updatable = true)
  private LocalDateTime mdfcnDt;

  @Builder
  public PgMemberEntity(Integer mbrNo, String mbrNm, String mbrBrdt, String mbrGndrCd, String mbrEmlAddr, String ciVl,
		String diVl, String mbrTelno, Integer regMbrNo, LocalDateTime regDt, Integer mdfcnMbrNo,
		LocalDateTime mdfcnDt) {
	super();
	this.mbrNo = mbrNo;
	this.mbrNm = mbrNm;
	this.mbrBrdt = mbrBrdt;
	this.mbrGndrCd = mbrGndrCd;
	this.mbrEmlAddr = mbrEmlAddr;
	this.ciVl = ciVl;
	this.diVl = diVl;
	this.mbrTelno = mbrTelno;
	this.regMbrNo = regMbrNo;
	this.regDt = regDt;
	this.mdfcnMbrNo = mdfcnMbrNo;
	this.mdfcnDt = mdfcnDt;
  }
 
}
