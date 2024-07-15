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
@Table(name = "tb_rstrnt_file")
@IdClass(RstrntFilePK.class)
public class RstrntFileEntity extends BaseEntity {

  @Id
  @Column(name = "rstrnt_sn")
  private Integer rstrntSn;

  @Id
  @Column(name = "file_sn")
  private Integer fileSn;



}
