package com.playground.api.restaurant.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "tb_rstrnt_menu_hashtag_mapng")
@IdClass(RstrntMenuHashtagMapngPK.class)
public class RstrntMenuHashtagMapngEntity extends BaseEntity {

  /**
   * 식당메뉴일련번호
   */
  @Id
  @Column(name = "rstrnt_menu_sn")
  private Integer rstrntMenuSn;
  
  /**
   * 해시태그일련번호
   */
  @Id
  @Column(name = "hashtag_sn")
  private Integer hashtagSn;
  
}
