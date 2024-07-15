package com.playground.api.event.entity;

import java.time.LocalDateTime;
import com.playground.entity.BaseEntity;
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
@Table(name = "tb_point")
@IdClass(PointEntityPK.class)
@SequenceGenerator(name = "tb_point_point_sn_seq", sequenceName = "tb_point_point_sn_seq", initialValue = 1, allocationSize = 1)
public class PointEntity extends BaseEntity {



  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_point_point_sn_seq")
  @Column(name = "point_sn")
  private Integer pointSn;

  @Id
  @Column(name = "mber_id")
  private String mberId;

  @Column(name = "point_value")
  private Integer pointValue;

  @Column(name = "valid_dt")
  private LocalDateTime validDt;

  @Column(name = "refrn_id")
  private String refrnId;

  @Column(name = "refrn_se_code_id")
  private String refrnSeCodeId;
}
