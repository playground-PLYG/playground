package com.playground.api.ai.service;

import java.util.List;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
  private final EmbeddingClient embeddingClient;

  /*
   * 문자열 목록 Embedding 조회
   */
  public EmbeddingResponse getEmbeddingList(List<String> messages) {
    return embeddingClient.embedForResponse(messages);
  }

  /*
   * 문자열 Embedding 조회
   */
  public EmbeddingResponse getEmbedding(String message) {
    return getEmbeddingList(List.of(message));
  }
}
