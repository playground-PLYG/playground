package com.playground.api.author.repository.dsl;

import java.util.List;
import com.playground.api.author.model.AuthorMenuResponse;

public interface AuthorMenuRepositoryCustom {
  List<AuthorMenuResponse> getAuthorMenuList(String authorId);

}
