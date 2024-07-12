package com.playground.api.restaurant.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "tb_rstrnt_file")
public class RstrntFileEntity extends BaseEntity {

  @Id
  @Column(name = "rstrnt_sn")
  private Integer rstrntSn;


  @Column(name = "file_sn")
  private Integer fileSn;



}
