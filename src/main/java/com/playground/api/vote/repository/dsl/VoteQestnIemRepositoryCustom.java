package com.playground.api.vote.repository.dsl;

import java.util.List;
import com.playground.api.vote.entity.VoteQestnIemEntity;

public interface VoteQestnIemRepositoryCustom {

  List<VoteQestnIemEntity> findByQestnSn(Integer voteSn, Integer qestnSn);

}
