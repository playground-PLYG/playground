package com.playground.api.sample.entity;

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

@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "member")
public class PagingEntity extends BaseEntity {

  /**
   * 아이디
   */
  @Id
  @Column(name = "user_id")
  private String userId;

  /**
   * 비밀번호
   */
  @Secret
  @Column
  private String password;

  /**
   * 이름
   */
  @Column
  private String name;

  /**
   * 이메일
   */
  @Column
  private String email;
}
