package com.playground.api.restaurant.repository.dsl;

import java.util.List;
import com.playground.api.restaurant.model.RstrntFileResponse;

public interface RstrntFileRepositoryCustom {

  List<RstrntFileResponse> findAllByRstrntSn(Integer rstrntSn);

  long updateRstrntImageFileSnById(Integer rstrntSn, Integer rstrntImageFileSn, Integer oldImageFileId);
}
