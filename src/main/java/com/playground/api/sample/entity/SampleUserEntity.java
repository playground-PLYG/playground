package com.playground.api.sample.entity;

import com.playground.annotation.Secret;
import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tb_mber")
public class SampleUserEntity extends BaseEntity {

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
   * 회원이메일주소
   */
  @Column(name = "mber_email_adres")
  private String mberEmailAdres;

  @Builder
  public SampleUserEntity(String mberId, String mberPassword, String mberNm, String mberEmailAdres) {
    super();
    this.mberId = mberId;
    this.mberPassword = mberPassword;
    this.mberNm = mberNm;
    this.mberEmailAdres = mberEmailAdres;
  }
}
