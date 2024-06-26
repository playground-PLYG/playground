package com.playground.api.metadata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.metadata.model.MetadataResponse;
import com.playground.api.metadata.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "metadata", description = "화면 Metadata API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/metadata")
public class MetadataController {
  private final MetadataService metadataService;

  /**
   * Metadata 조회
   */
  @Operation(summary = "Metadata 조회", description = "SEO를 위한 Metadata 조회")
  @GetMapping
  public MetadataResponse getMetadata(@RequestParam String url) {
    return metadataService.getMetadata(url);
  }
}
