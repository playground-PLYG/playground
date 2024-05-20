package com.playground.api.notice.repository.dsl;

import static com.playground.api.notice.entity.QCommentEntity.commentEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.notice.entity.CommentEntity;
import com.playground.api.notice.entity.CommentEntityPK;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<CommentEntity> getCommentList(CommentEntityPK request) {
    return queryFactory.selectFrom(commentEntity)
        .where(commentEntity.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
            .and(commentEntity.postEntity.nttSn.eq(request.getPostEntity().getNttSn())))
        .orderBy(commentEntity.upperCmntSn.asc().nullsFirst(), commentEntity.cmntSn.asc()).fetch();
  }

}
