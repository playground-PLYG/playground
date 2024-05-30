package com.playground.api.file.repository;

import org.springframework.data.repository.CrudRepository;
import com.playground.api.file.entity.FileEntity;

public interface FileRepository extends CrudRepository<FileEntity, Integer> {
}
