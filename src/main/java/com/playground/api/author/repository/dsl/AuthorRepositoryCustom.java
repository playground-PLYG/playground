package com.playground.api.author.repository.dsl;

import java.util.List;
import com.playground.api.author.model.AuthorResponse;

public interface AuthorRepositoryCustom {
  List<AuthorResponse> getAuthorList(String authorId, String authorNm, String deleteAt);

}
