package com.playground.api.code.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "tb_code")
public class CodeEntity extends BaseEntity {
  /**
   * 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "code_sn")
  private Integer codeSn;

  /**
   * 코드id
   */
  @Column(name = "code_id")
  private String codeId;

  /**
   * 코드명
   */
  @Column(name = "code_nm")
  private String codeNm;

  /**
   * 상위코드id
   */
  @Column(name = "upper_code_id")
  private String upperCodeId;

  /**
   * 그룹코드여부
   */
  @Column(name = "group_code_at")
  private String groupCodeAt;

  /**
   * 정렬 순번
   */
  @Column(name = "sort_ordr")
  private Integer sortOrdr;
}
