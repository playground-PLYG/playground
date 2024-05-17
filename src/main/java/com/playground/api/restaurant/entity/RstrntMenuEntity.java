package com.playground.api.restaurant.entity;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.annotations.Type;
import com.playground.entity.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "tb_rstrnt_menu")
@SequenceGenerator(name = "tb_rstrnt_menu_rstrnt_menu_sn_seq", sequenceName = "tb_rstrnt_menu_rstrnt_menu_sn_seq", initialValue = 1,
    allocationSize = 1)
@IdClass(RstrntMenuPK.class)
public class RstrntMenuEntity extends BaseEntity {
  /**
   * 식당일련번호
   */
  @Id
  @Column(name = "rstrnt_sn")
  private Integer rstrntSn;

  /**
   * 식당메뉴일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_rstrnt_menu_rstrnt_menu_sn_seq")
  @Column(name = "rstrnt_menu_sn")
  private Integer rstrntMenuSn;

  /**
   * 식당메뉴명
   */
  @Column(name = "rstrnt_menu_nm")
  private String rstrntMenuNm;

  /**
   * 식당이미지 URL
   */
  @Column(name = "rstrnt_menu_image_url")
  private String rstrntMenuImageUrl;

  /**
   * 식당메뉴가격
   */
  @Column(name = "rstrnt_menu_pc")
  private BigDecimal rstrntMenuPc;

  /**
   * embedding
   */
  @Type(JsonType.class)
  @Column(name = "embedding", columnDefinition = "vector")
  private List<Double> embedding;

  public void changeEmbedding(List<Double> embedding) {
    this.embedding = embedding;
  }
}
