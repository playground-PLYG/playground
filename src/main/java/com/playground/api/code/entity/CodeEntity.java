package com.playground.api.code.entity;

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
  @Column(name = "code_sn")
  private String codeSn;

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

  @Builder
  public CodeEntity(String codeSn, String codeId, String codeNm, String upperCodeId, String groupCodeAt, Integer sortOrdr) {
    super();
    this.codeSn = codeSn;
    this.codeId = codeId;
    this.codeNm = codeNm;
    this.upperCodeId = upperCodeId;
    this.groupCodeAt = groupCodeAt;
    this.sortOrdr = sortOrdr;
  }

}
