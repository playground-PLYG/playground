package com.playground.api.event.repository.dsl;

import static com.playground.api.event.entity.QEventEntity.eventEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.event.entity.EventEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<EventEntity> getEventList(EventEntity req, Pageable pageable) {
    List<EventEntity> event = queryFactory
        .select(Projections.fields(EventEntity.class, eventEntity.eventSn, eventEntity.eventNm, eventEntity.eventSeCodeId, eventEntity.eventBeginDt,
            eventEntity.eventEndDt, eventEntity.registUsrId, eventEntity.registDt, eventEntity.updtUsrId, eventEntity.updtDt,
            new CaseBuilder().when(eventEntity.eventEndDt.lt(LocalDateTime.now())).then("종료").when(eventEntity.eventBeginDt.loe(LocalDateTime.now()))
                .then("진행중").otherwise("예정").as("progrsSttus")))
        .from(eventEntity).where(eventNmLkie(req.getEventNm()), eventSeCodeIdLkie(req.getEventSeCodeId()), progrsSttusSch(req.getProgrsSttus()))
        .orderBy(eventEntity.registDt.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery = queryFactory.select(eventEntity.count()).from(eventEntity).where(eventNmLkie(req.getEventNm()));

    return PageableExecutionUtils.getPage(event, pageable, countQuery::fetchOne);
  }

  /* 이벤트명 조회 동적쿼리 */
  private BooleanExpression eventNmLkie(String eventNm) {
    return StringUtils.isNotBlank(eventNm) ? eventEntity.eventNm.like(eventNm + "%") : null;
  }

  /* 이벤트구분코드 조회 동적쿼리 */
  private BooleanExpression eventSeCodeIdLkie(String eventSeCodeId) {
    return StringUtils.isNotBlank(eventSeCodeId) ? eventEntity.eventSeCodeId.like(eventSeCodeId + "%") : null;
  }

  /* 진행상태 조회 동적쿼리 */
  private BooleanExpression progrsSttusSch(String progrsSttus) {
    if (StringUtils.isNotBlank(progrsSttus)) {
      if (progrsSttus.equals("종료")) {
        return eventEntity.eventEndDt.lt(LocalDateTime.now());
      } else if (progrsSttus.equals("진행중")) {
        return eventEntity.eventBeginDt.loe(LocalDateTime.now()).and(eventEntity.eventEndDt.goe(LocalDateTime.now()));
      } else if (progrsSttus.equals("예정")) {
        return eventEntity.eventBeginDt.gt(LocalDateTime.now());
      }
    }
    return null;
  }

}

