package com.playground.api.menu.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
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
  @Column(name = "menu_id")
  private Integer menuId;

  /** 메뉴명 */
  @Column(name = "menu_nm")
  private String menuNm;

  /** 메뉴URL */
  @Column(name = "menu_url")
  private String menuUrl;

  /** 메뉴레벨 */
  @Column(name = "menu_lvl")
  private String menuLvl;

  /** 정렬순서 */
  @Column(name = "menu_sort_order")
  private String menuSortOrder;

  /** 상위메뉴ID */
  @Column(name = "parent_menu_id")
  private String parentMenuId;

  /** 등록자 */
  @Column(name = "reg_mbr_no")
  private Integer regMbrNo;

  /** 등록일시 */
  @Column(name = "reg_dt", insertable = false, updatable = false)
  private LocalDateTime regDt;

  /** 수정자 */
  @Column(name = "mdfcn_mbr_no")
  private Integer mdfcnMbrNo;

  /** 수정일시 */
  @UpdateTimestamp
  @Column(name = "mdfcn_dt", insertable = false, updatable = true)
  private LocalDateTime mdfcnDt;

  /** 사용여부 */
  @Column(name = "use_yn", insertable = false, updatable = true)
  private String useYn;
  
  @Builder
  public MenuEntity(Integer menuId, String menuNm, String menuUrl, String menuLvl, String menuSortOrder, String parentMenuId, Integer regMbrNo, LocalDateTime regDt, Integer mdfcnMbrNo, LocalDateTime mdfcnDt, String useYn) {
    super();
    this.menuId = menuId;
    this.menuNm = menuNm;
    this.menuUrl = menuUrl;
    this.menuLvl = menuLvl;
    this.menuSortOrder = menuSortOrder;
    this.parentMenuId = parentMenuId;
    this.regMbrNo = regMbrNo;
    this.regDt = regDt;
    this.mdfcnMbrNo = mdfcnMbrNo;
    this.mdfcnDt = mdfcnDt;
    this.useYn = useYn;
  }
}
