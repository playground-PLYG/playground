package com.playground.api.menu.repository.dsl;

import java.util.List;
import com.playground.api.menu.model.MenuResponse;

public interface MenuRepositoryCustom {
  List<MenuResponse> getMenuList(String mberId);

  List<MenuResponse> getSelectByCondition(String menuNm, String menuUrl, String useAt);
}
