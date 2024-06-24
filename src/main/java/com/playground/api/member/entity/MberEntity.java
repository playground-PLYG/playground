package com.playground.api.member.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.playground.annotation.Secret;
import com.playground.entity.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "tb_mber")
public class MberEntity extends BaseEntity implements UserDetails{

  private static final long serialVersionUID = -7358017835959401676L;

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
  
  @Column(name= "author_id")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name= "TB_MBER_AUTHOR_MAPNG", joinColumns = 
    @JoinColumn(name = "mber_id")
  )
  @Builder.Default
  private List<String> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.roles.stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return mberPassword;
  }

  @Override
  public String getUsername() {
    return mberNm;
  }
}
