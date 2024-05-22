package com.playground.api.board.repository.dsl;

import java.util.List;

import com.playground.api.board.entity.CommentEntity;
import com.playground.api.board.entity.CommentEntityPK;

public interface CommentRepositoryCustom {

  List<CommentEntity> getCommentList(CommentEntityPK request);
}
