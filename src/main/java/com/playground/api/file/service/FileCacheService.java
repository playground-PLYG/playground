package com.playground.api.file.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Storage;
import com.playground.api.file.entity.FileEntity;
import com.playground.api.file.model.ImageResponse;
import com.playground.constants.CacheType;
import com.playground.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileCacheService {
  private final Storage storage;

  @Value("${spring.cloud.gcp.storage.bucket}")
  private String bucketName;

  @Cacheable(cacheManager = CacheType.ONE_YEAR, cacheNames = "images", key = "#fileEntity.fileSn", unless = "#result == null")
  public ImageResponse getImageCache(FileEntity fileEntity) {
    log.debug(">>> none cached");

    try (ReadChannel reader = storage.reader(bucketName, fileEntity.getStreFileNm())) {
      ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);

      List<Byte> byteList = new ArrayList<>();

      while (reader.read(bytes) > 0) {
        bytes.flip();

        for (int i = 0; i < bytes.limit(); i++) {
          byteList.add(bytes.get(i));
        }

        bytes.clear();
      }

      byte[] fileContent = new byte[byteList.size()];
      int i = 0;

      for (Byte b : byteList) {
        fileContent[i++] = b;
      }

      return ImageResponse.builder().img(fileContent).mediaType(fileEntity.getCntntsTyCn()).build();
    } catch (IOException e) {
      throw new BizException("파일 다운로드에 실패했습니다.");
    }
  }
}
