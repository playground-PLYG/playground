package com.playground.api.restaurant.entity;

import java.math.BigDecimal;
import java.util.List;
import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tb_rstrnt")
public class RstrntEntity extends BaseEntity {
  /**
   * 식당일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rstrnt_sn")
  private Integer rstrntSn;

  /**
   * 식당명
   */
  @Column(name = "rstrnt_nm")
  private String rstrntNm;

  /**
   * 위도위치
   */
  @Column(name = "la_lc")
  private String laLc;

  /**
   * 경도위치
   */
  @Column(name = "lo_lc")
  private String loLc;

  /**
   * 카카오지도ID
   */
  @Column(name = "kakao_map_id")
  private String kakaoMapId;

  /**
   * 식당종류코드
   */
  @Column(name = "rstrnt_knd_code")
  private String rstrntKndCode;

  /**
   * 식당거리
   */
  @Column(name = "rstrnt_dstnc")
  private BigDecimal rstrntDstnc;


  /**
   * 식당 파일
   */
  @OneToMany(mappedBy = "rstrntSn", fetch = FetchType.LAZY)
  private List<RstrntFileEntity> rstrntFiles;

}
