package com.playground.api.member.entity;

import com.playground.annotation.Secret;
import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
=======
import lombok.NoArgsConstructor;
>>>>>>> branch 'master' of https://github.com/playground-PLYG/playground.git

@Builder
@AllArgsConstructor
<<<<<<< HEAD
@RequiredArgsConstructor
=======
@NoArgsConstructor
>>>>>>> branch 'master' of https://github.com/playground-PLYG/playground.git
@EqualsAndHashCode(callSuper = true)
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
}
