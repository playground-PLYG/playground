package com.playground.api.event.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.PointPaymentEntity;


public interface PointPaymentRepository extends JpaRepository<PointPaymentEntity, Integer> {
  void deleteByEventSn(int eventSn);

  List<PointPaymentEntity> findByEventSn(Integer eventSn);
}
