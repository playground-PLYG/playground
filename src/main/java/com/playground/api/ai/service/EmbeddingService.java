package com.playground.api.ai.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
  private final CodeService codeService;
  private final EmbeddingClient embeddingClient;

  private static final String EMBEDDING_USE_YN = "EMBEDDING_USE_YN";
  private static final String YN_Y = "Y";

  /*
   * 문자열 목록 Embedding 조회
   */
  public List<Embedding> getEmbeddingList(List<String> messages) {
    CodeSrchResponse code = codeService.getCode(CodeSrchRequest.builder().upperCode(EMBEDDING_USE_YN).code(YN_Y).build());

    if (StringUtils.equals(YN_Y, code.getCode())) {
      EmbeddingResponse embeddingResponse = embeddingClient.embedForResponse(messages);

      return embeddingResponse.getResults();
    } else {
      return List.of();
    }
  }

  /*
   * 문자열 Embedding 조회
   */
  public Embedding getEmbedding(String message) {
    CodeSrchResponse code = codeService.getCode(CodeSrchRequest.builder().upperCode(EMBEDDING_USE_YN).code(YN_Y).build());

    if (StringUtils.equals(YN_Y, code.getCode())) {
      EmbeddingResponse embeddingResponse = embeddingClient.embedForResponse(List.of(message));

      return embeddingResponse.getResult();
    } else {
      return new Embedding(null, null);
    }
  }
}
