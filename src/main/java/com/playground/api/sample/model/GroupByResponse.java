package com.playground.api.sample.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import com.playground.api.sample.entity.SmpleDetailEntity;
import com.playground.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class GroupByResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 샘플 일련번호
   */
  private Integer smpleSn;

  /**
   * 샘플 첫번째 내용
   */
  private String smpleFirstCn;

  /**
   * 샘플 두번째 내용
   */
  private String smpleSeconCn;

  /**
   * 샘플 세번째 내용
   */
  private String smpleThrdCn;
  
  /**
   * detail List
   */
  private final List<SmpleDetailEntity> smpleDetailEntityList = new ArrayList<>();
  
  
}
