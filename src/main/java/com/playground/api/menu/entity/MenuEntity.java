package com.playground.api.menu.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_menu")
public class MenuEntity extends BaseEntity {

  /** 메뉴ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_sn")
  private Integer menuSn;

  /** 메뉴명 */
  @Column(name = "menu_nm")
  private String menuNm;

  /** 메뉴URL */
  @Column(name = "menu_url")
  private String menuUrl;

  /** 메뉴레벨 */
  @Column(name = "menu_depth")
  private Integer menuDepth;

  /** 정렬순서 */
  @Column(name = "menu_sort_ordr")
  private Integer menuSortOrdr;

  /** 상위메뉴ID */
  @Column(name = "upper_menu_sn")
  private String upperMenuSn;

  /** 사용여부 */
  @Column(name = "use_at")
  private String useAt;

  @Builder
  public MenuEntity(Integer menuSn, String menuNm, String menuUrl, Integer menuDepth, Integer menuSortOrdr, String upperMenuSn, String useAt) {
    super();
    this.menuSn = menuSn;
    this.menuNm = menuNm;
    this.menuUrl = menuUrl;
    this.menuDepth = menuDepth;
    this.menuSortOrdr = menuSortOrdr;
    this.upperMenuSn = upperMenuSn;
    this.useAt = useAt;
  }

}
