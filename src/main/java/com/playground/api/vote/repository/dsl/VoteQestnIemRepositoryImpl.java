package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.QVoteQestnIemEntity;
import com.playground.api.vote.entity.VoteQestnIemEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class VoteQestnIemRepositoryImpl implements VoteQestnIemRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QVoteQestnIemEntity tbIem = QVoteQestnIemEntity.voteQestnIemEntity;


  @Override
  public List<VoteQestnIemEntity> findByQestnSn(Integer voteSn, Integer qestnSn) {
    return queryFactory.selectFrom(tbIem).where(tbIem.voteSn.eq(voteSn), tbIem.qestnSn.eq(qestnSn)).fetch();
  }


}
