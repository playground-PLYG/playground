package com.playground.api.author.repository.dsl;

import java.util.List;
import com.playground.api.author.model.MberAuthorResponse;

public interface MberAuthorRepositoryCustom {
  List<MberAuthorResponse> getMberAuthorList(String mberId);

}
