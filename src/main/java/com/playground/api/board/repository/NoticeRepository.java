package com.playground.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.api.board.entity.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, String >{

}
