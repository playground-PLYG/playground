package com.playground.api.sample.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.sample.entity.SmpleEntity;

public interface SmpleRepositoryCustom {

  SmpleEntity getSmpleSn(String fstCn, String secCn, String thrdCn);
  
  List<SmpleEntity> getSmpleSnList(String fstCn, String secCn, String thrdCn);
  
  Page<SmpleEntity> getSmpleSnPageList(String fstCn, String secCn, String thrdCn, Pageable pageable);
}
