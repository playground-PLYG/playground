package com.playground.api.member.entity;

import com.playground.annotation.Secret;
import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_mber")
public class MberEntity extends BaseEntity {

  /**
   * 회원ID
   */
  @Id
  @Column(name = "mber_id")
  private String mberId;

  /**
   * 비밀번호
   */
  @Secret
  @Column(name = "mber_password")
  private String mberPassword;

  /**
   * 회원명
   */
  @Column(name = "mber_nm")
  private String mberNm;

  /**
   * 회원생년월일
   */
  @Column(name = "mber_bymd")
  private String mberBymd;

  /**
   * 회원성별코드
   */
  @Column(name = "mber_sexdstn_code")
  private String mberSexdstnCode;

  /**
   * 회원이메일주소
   */
  @Column(name = "mber_email_adres")
  private String mberEmailAdres;

  /**
   * CI내용
   */
  @Column(name = "ci_cn")
  private String ciCn;

  /**
   * DI내용
   */
  @Column(name = "di_cn")
  private String diCn;

  /**
   * 회원전화번호
   */
  @Column(name = "mber_telno")
  private String mberTelno;

  @Builder
  public MberEntity(String mberId, String mberPassword, String mberNm, String mberBymd, String mberSexdstnCode, String mberEmailAdres, String ciCn,
      String diCn, String mberTelno) {
    super();
    this.mberId = mberId;
    this.mberPassword = mberPassword;
    this.mberNm = mberNm;
    this.mberBymd = mberBymd;
    this.mberSexdstnCode = mberSexdstnCode;
    this.mberEmailAdres = mberEmailAdres;
    this.ciCn = ciCn;
    this.diCn = diCn;
    this.mberTelno = mberTelno;
  }
}
