package com.playground.api.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.file.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
