package com.playground.api.event.entity;

import java.time.LocalDateTime;
import com.playground.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tb_event")
@SequenceGenerator(name = "tb_event_event_sn_seq", sequenceName = "tb_event_event_sn_seq", initialValue = 1, allocationSize = 1)
public class EventEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_event_event_sn_seq")
  @Column(name = "event_sn")
  private Integer eventSn;

  @Column(name = "event_nm")
  private String eventNm;

  @Column(name = "event_begin_dt")
  private LocalDateTime eventBeginDt;

  @Column(name = "event_end_dt")
  private LocalDateTime eventEndDt;

  @Column(name = "progrs_sttus")
  private String progrsSttus;

  @Column(name = "event_thumb_file_sn")
  private Integer eventThumbFileSn;

  @Column(name = "przwner_co")
  private Integer przwnerCo;

  @Column(name = "event_se_code_id")
  private String eventSeCodeId;

  @Column(name = "drwt_mthd_code_id")
  private String drwtMthdCodeId;

  @Column(name = "point_pymnt_mthd_code_id")
  private String pointPymntMthdCodeId;

  @Column(name = "tot_point_value")
  private Integer totPointValue;

  @Column(name = "cntnts_cn")
  private String cntntsCn;

  @Column(name = "expsr_at")
  private String expsrAt;

  @Column(name = "drwt_dt")
  private LocalDateTime drwtDt;

}
