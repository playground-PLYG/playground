package com.playground.api.board.repository.dsl;

import java.util.List;
import com.playground.api.board.entity.PostEntity;

public interface PostRepositoryCustom {

  List<PostEntity> getPostList(PostEntity request);
}
