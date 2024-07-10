package com.playground.api.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.PointPaymentEntity;

public interface PointPaymentRepository extends JpaRepository<PointPaymentEntity, Integer> {

}
