package com.playground.api.sample.repository.dsl;

//해당 클래스에서 사용할 Q클래스 테이블 설정
import static com.playground.api.sample.entity.QSmpleEntity.smpleEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.sample.entity.SmpleEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SmpleRepositoryImpl implements SmpleRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  
  /* 샘플일련번호 단건 조회 */
  @Override
  public SmpleEntity getSmpleSn(String fstCn, String secCn, String thrdCn) {
    return queryFactory
        .selectFrom(smpleEntity)
        .where(fstCnEq(fstCn), secCnEq(secCn), thrdCnEq(thrdCn))
        .fetchFirst();
  }
  
  /* 샘플일련번호 다건 조회 */
  @Override
  public List<SmpleEntity> getSmpleSnList(String fstCn, String secCn, String thrdCn) {
    return queryFactory
        .selectFrom(smpleEntity)
        .where(fstCnEq(fstCn), secCnEq(secCn), thrdCnEq(thrdCn))
        .fetch();
  }
  
  @Override
  public Page<SmpleEntity> getSmpleSnPageList(String fstCn, String secCn, String thrdCn, Pageable pageable) {
    
    List<SmpleEntity> content = queryFactory
                                .selectFrom(smpleEntity)
                                .where(fstCnEq(fstCn), secCnEq(secCn), thrdCnEq(thrdCn))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();
    
    JPAQuery<Long> countQuery = queryFactory
                                .select(smpleEntity.count())
                                .from(smpleEntity)
                                .where(fstCnEq(fstCn), secCnEq(secCn), thrdCnEq(thrdCn));
    
    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }
  
  /* 동적쿼리를 위한 함수 */
  private BooleanExpression fstCnEq(String fstCn) {
    if(fstCn == null) {
      return null;
    }
    
    return smpleEntity.smpleFirstCn.eq(fstCn);
  }
  
  private BooleanExpression secCnEq(String secCn) {
    if(secCn == null) {
      return null;
    }
    
    return smpleEntity.smpleSeconCn.eq(secCn);
  }
  
  private BooleanExpression thrdCnEq(String thrdCn) {
    if(thrdCn == null) {
      return null;
    }
    
    return smpleEntity.smpleThrdCn.eq(thrdCn);
  }


}
