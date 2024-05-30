package com.playground.api.file.entity;

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
@Table(name = "tb_file")
public class FileEntity extends BaseEntity {
  /**
   * 파일일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "file_sn")
  private Integer fileSn;

  /**
   * 원본파일명
   */
  @Column(name = "orginl_file_nm")
  private String orginlFileNm;

  /**
   * 원본파일 확장자
   */
  @Column(name = "orginl_file_extsn_nm")
  private String orginlFileExtsnNm;

  /**
   * 저장파일명
   */
  @Column(name = "stre_file_nm")
  private String streFileNm;

  /**
   * 컨텐츠타입내용
   */
  @Column(name = "cntnts_ty_cn")
  private String cntntsTyCn;

  /**
   * 파일용량
   */
  @Column(name = "file_cpcty")
  private Long fileCpcty;

  /**
   * 파일첫번째속성명
   */
  @Column(name = "file_one_atrb_nm")
  private String fileOneAtrbNm;

  /**
   * 파일두번째속성명
   */
  @Column(name = "file_two_atrb_nm")
  private String fileTwoAtrbNm;

  /**
   * 파일세번째속성명
   */
  @Column(name = "file_three_atrb_nm")
  private String fileThreeAtrbNm;
}
