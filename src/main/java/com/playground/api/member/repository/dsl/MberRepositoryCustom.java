package com.playground.api.member.repository.dsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.member.entity.MberEntity;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberModifyInfoRequest;

public interface MberRepositoryCustom {
  MberInfoResponse findByIdDetail(String userId);

  Page<MberEntity> getMberPageList(String mberId, String mberNm, Pageable pageable);

  void updateMberinfoByMberId(MberModifyInfoRequest req);
}
