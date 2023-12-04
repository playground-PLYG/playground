package com.playground.api.code.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

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
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_code")
public class CodeEntity extends BaseEntity {
	
  /**
   * 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sn")
  private String sn;	
	
	

  /**
   * 코드id
   */
  @Column(name = "cd_id")
  private String cdId;
  
  /**
   * 코드명
   */
  
  @Column(name = "cd_nm")
  private String cdNm;
  
  /**
   * 상위코드id
   */
  
  @Column(name = "up_cd_id")
  private String upCdId;

  /**
   * 그룹코드여부
   */
  
  @Column(name = "group_cd_yn")
  private String groupCdYn;
  
  /**
   * 정렬 순번
   */
  
  @Column(name = "sort_sn")
  private String sortSn;
  
  /**
   * 등록자ID
   */
  @CreatedBy
  @Column(name = "reg_mbr_no", updatable = false)
  private String regMbrNo;
  
  /**
   * 등록일시
   */
  @CreationTimestamp
  @Column(name = "reg_dt", updatable = false)
  private Timestamp regDt;
  
  /**
   * 수정자ID
   */
  @LastModifiedBy
  @Column(name = "mdfcn_mbr_no")
  private String mdfcnMbrNo;
  
  /**
   * 등록일시
   */
  @UpdateTimestamp 
  @Column(name = "mdfcn_dt")
  private Timestamp dfcnDt;
  

  @Builder
  public CodeEntity(String sn,String cdId, String cdNm,String upCdId, String groupCdYn, String sortSn, String regMbrNo, Timestamp regDt, String mdfcnMbrNo, Timestamp dfcnDt ) {
    super();
    this.sn = sn;
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.upCdId = upCdId;
    this.groupCdYn = groupCdYn;
    this.sortSn = sortSn;
    this.regMbrNo = regMbrNo;
    this.regDt = regDt;
    this.mdfcnMbrNo = mdfcnMbrNo;
    this.dfcnDt = dfcnDt;

  }

}
