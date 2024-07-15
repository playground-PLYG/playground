package com.playground.api.event.entity;

import java.time.LocalDateTime;
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
@Table(name = "tb_event_participate")
@IdClass(EventParticipateEntityPK.class)
public class EventParticipateEntity extends BaseEntity {

  @Id
  @Column(name = "event_sn")
  private Integer eventSn;

  @Id
  @Column(name = "mber_id")
  private String mberId;

  @Column(name = "przwin_point_value")
  private Integer przwinPointValue;

  @Column(name = "event_przwin_at")
  private String eventPrzwinAt;

  @Column(name = "event_partcptn_dt")
  private LocalDateTime eventPartcptnDt;

}
