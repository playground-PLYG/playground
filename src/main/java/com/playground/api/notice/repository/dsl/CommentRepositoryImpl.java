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
public class CommentRepositoryImpl implements CommentRepositoryCustom{
  
  private final JPAQueryFactory queryFactory;
  
  @Override
  public List<CommentEntity> getCommentList(CommentEntityPK request) {
    /*
    List<CommentResponse> res = new ArrayList<>();
    
    List<CommentEntity> comments = queryFactory
        .selectFrom(commentEntity)
        .innerJoin(commentEntity.postEntity.noticeEntity, noticeEntity)
        .innerJoin(commentEntity.postEntity, postEntity)
        .where(commentEntity.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
            ,commentEntity.postEntity.nttNo.eq(request.getPostEntity().getNttNo()).and(commentEntity.parent.upperCmntNo.eq(0)))
        .orderBy(commentEntity.cmntNo.asc())
        .fetch();
    
    List<CommentEntity> childComments = queryFactory
        .selectFrom(commentEntity)
        .innerJoin(commentEntity.postEntity.noticeEntity, noticeEntity)
        .innerJoin(commentEntity.postEntity, postEntity)
        .where(commentEntity.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
            ,commentEntity.postEntity.nttNo.eq(request.getPostEntity().getNttNo()).and(commentEntity.parent.cmntNo.ne(0)))
        .fetch();
    
    comments.stream()
    .forEach(parent -> {
        CommentEntity.builder().children(childComments.stream()
                .filter(child -> child.getUpperCmntNo().equals(parent.getCmntNo()))
                .collect(Collectors.toList())).build();
    });
    
    
    return res;
    */
    
    return queryFactory.selectFrom(commentEntity)
        .leftJoin(commentEntity.parent)
        .fetchJoin()
        .where(commentEntity.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
            .and(commentEntity.postEntity.nttNo.eq(request.getPostEntity().getNttNo())))
        .orderBy(
            commentEntity.parent.upperCmntNo.asc().nullsFirst()
            ,commentEntity.registDt.asc())
        .fetch();
        
     
    
  }

}
