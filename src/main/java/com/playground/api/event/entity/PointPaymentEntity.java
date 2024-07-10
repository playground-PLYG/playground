package com.playground.api.event.entity;

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
@Table(name = "tb_event_point_payment")
@IdClass(PointPaymentEntityPK.class)
@SequenceGenerator(name = "tb_event_point_payment_point_pymnt_sn_seq", sequenceName = "tb_event_point_payment_point_pymnt_sn_seq", initialValue = 1,
    allocationSize = 1)
public class PointPaymentEntity extends BaseEntity {

  @Id
  @Column(name = "event_sn")
  private Integer eventSn;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_event_point_payment_point_pymnt_sn_seq")
  @Column(name = "point_pymnt_sn")
  private Integer pointPymntSn;

  @Column(name = "point_pymnt_unit_value")
  private Integer pointPymntUnitValue;

  @Column(name = "fixing_point_payr_co")
  private Integer fixingPointPayrCo;

  @Column(name = "fixing_point_value")
  private Integer fixingPointValue;

}
