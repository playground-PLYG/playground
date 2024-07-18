package com.playground.api.event.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.event.entity.PointPaymentEntityPK;


public interface PointPaymentRepository extends JpaRepository<PointPaymentEntity, PointPaymentEntityPK> {
  void deleteByEventSn(int eventSn);

  List<PointPaymentEntity> findByEventSn(Integer eventSn);
}
