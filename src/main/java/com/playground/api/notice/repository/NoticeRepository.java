package com.playground.api.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.notice.entity.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, String >{

}
