package com.playground.api.notice.repository.dsl;

import java.util.List;
import com.playground.api.notice.entity.CommentEntity;
import com.playground.api.notice.entity.CommentEntityPK;

public interface CommentRepositoryCustom {

  List<CommentEntity> getCommentList(CommentEntityPK request);
}
