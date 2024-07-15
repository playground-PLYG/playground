package com.playground.api.sample.entity;

import com.playground.annotation.ExcelDown;
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
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "tb_mber")
public class ExcelDownEntity extends BaseEntity {

  @Id
  @Column(name = "mber_id")
  @ExcelDown(headerName = "아이디", dataFormat = "내 아이디는 : @", order = 1, width = 150)
  private String mberId;

  @Secret
  @Column(name = "mber_password")
  @ExcelDown(headerName = "비밀번호")
  private String mberPassword;

  @Column(name = "mber_nm")
  @ExcelDown(headerName = "이름", order = 2)
  private String mberNm;

  @Column(name = "mber_email_adres")
  @ExcelDown(headerName = "이메일", width = 150)
  private String mberEmailAdres;
}
