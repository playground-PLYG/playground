package com.playground.api.board.repository.dsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.board.entity.PostEntity;

public interface PostRepositoryCustom {

  Page<PostEntity> getPostList(PostEntity request, Pageable pageable);
}
