package com.playground.api.notice.repository.dsl;

import static com.playground.api.notice.entity.QCommentEntity.commentEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.notice.entity.CommentEntity;
import com.playground.api.notice.entity.CommentEntityPK;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    
    log.debug("request.getPostEntity().getNoticeEntity() ::: {}", request.getPostEntity().getNoticeEntity());
    log.debug("request.getPostEntity().getNttSn() ::: {}", request.getPostEntity().getNttSn());
    return queryFactory
        .selectFrom(commentEntity)
        .where(commentEntity.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
            .and(commentEntity.postEntity.nttSn.eq(request.getPostEntity().getNttSn())))
        .orderBy(
            commentEntity.upperCmntSn.asc().nullsFirst()
            ,commentEntity.cmntSn.asc())
        .fetch();
    
    /*
    List<CommentResponse> query = queryFactory
    .select(Projections.fields(CommentResponse.class
        ,commentEntity.cmntSn.as("commentNo")
        ,commentEntity.postEntity.noticeEntity.bbsId.as("boardId")
        ,commentEntity.postEntity.nttSn.as("noticeNo")
        ,commentEntity.cmntCn.as("commentCn")
        ,commentEntity.upperCmntSn.as("upperCommentNo")
        ))
    .from(commentEntity)
    .leftJoin(commentEntity.parent)
    .where(commentEntity.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
        .and(commentEntity.postEntity.nttSn.eq(request.getPostEntity().getNttSn())))
    .orderBy(
        commentEntity.parent.upperCmntSn.asc().nullsFirst()
        ,commentEntity.registDt.asc())
    .fetch();
    */
    /*
    QCommentEntity parent = new QCommentEntity("parent");
    QCommentEntity child = new QCommentEntity("child");
    
     queryFactory
        .selectFrom(parent)
        .distinct()
        .leftJoin(parent.children, child)
        .fetchJoin()
        .where(parent.postEntity.noticeEntity.bbsId.eq(request.getPostEntity().getNoticeEntity())
            .and(parent.postEntity.nttSn.eq(request.getPostEntity().getNttSn()))
            .and(parent.upperCmntSn.eq(0))
            )
        .orderBy(
            parent.upperCmntSn.asc().nullsFirst()
            ,parent.registDt.asc())
        .fetch();
        
     return null;
     */
    
  }

}
