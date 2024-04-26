package com.playground.api.ai.service;

import java.util.List;
import org.springframework.ai.embedding.Embedding;
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
  public List<Embedding> getEmbeddingList(List<String> messages) {
    EmbeddingResponse embeddingResponse = embeddingClient.embedForResponse(messages);

    return embeddingResponse.getResults();
  }

  /*
   * 문자열 Embedding 조회
   */
  public Embedding getEmbedding(String message) {
    EmbeddingResponse embeddingResponse = embeddingClient.embedForResponse(List.of(message));

    return embeddingResponse.getResult();
  }
}
