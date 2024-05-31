package com.playground.api.file.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.playground.api.file.entity.FileEntity;
import com.playground.api.file.model.FileListSrchRequest;
import com.playground.api.file.model.FileRemoveRequest;
import com.playground.api.file.model.FileResponse;
import com.playground.api.file.model.FileSaveRequest;
import com.playground.api.file.repository.FileRepository;
import com.playground.exception.BizException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
  private final FileRepository fileRepository;

  private final Storage storage;

  @Value("${spring.cloud.gcp.storage.bucket}")
  private String bucketName;

  public FileResponse saveFile(FileSaveRequest reqData) {
    if (reqData == null || reqData.getFile() == null) {
      throw new BizException("업로드할 파일이 없습니다.");
    }

    String uuid = UUID.randomUUID().toString().replace("-", "");
    String contentType = reqData.getFile().getContentType();
    String originalFilename = reqData.getFile().getOriginalFilename();
    Long fileSize = reqData.getFile().getSize();
    String fileName = FilenameUtils.getBaseName(originalFilename);
    String fileExt = FilenameUtils.getExtension(originalFilename);

    if (StringUtils.isBlank(fileName)) {
      throw new BizException("파일명이 없는 파일은 업로드 할 수 없습니다.");
    }

    if (StringUtils.isBlank(fileExt)) {
      throw new BizException("파일확장자가 없는 파일은 업로드 할 수 없습니다.");
    }

    if (fileSize == 0) {
      throw new BizException("파일 사이즈가 0인 파일은 업로드 할 수 없습니다.");
    }

    // TODO apache tika활용해서 확장자 위변조 체크

    BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid).setContentType(contentType).build();

    try {
      storage.create(blobInfo, reqData.getFile().getBytes());
    } catch (IOException e) {
      throw new BizException("파일 업로드에 실패했습니다.");
    }

    FileEntity fileEntity = fileRepository.save(
        FileEntity.builder().orginlFileNm(fileName).orginlFileExtsnNm(fileExt).streFileNm(uuid).cntntsTyCn(contentType).fileCpcty(fileSize).build());

    return FileResponse.builder().fileId(fileEntity.getFileSn()).fileName(originalFilename).fileSize(fileSize).build();
  }

  public ResponseEntity<byte[]> getFile(Integer fileId) {
    FileEntity fileEntity = fileRepository.findById(fileId).orElse(null);

    if (fileEntity == null) {
      throw new BizException("다운로드할 파일이 없습니다.");
    }

    String fileName = fileEntity.getOrginlFileNm() + "." + fileEntity.getOrginlFileExtsnNm();

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

      return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
          .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").body(fileContent);
    } catch (IOException e) {
      throw new BizException("파일 다운로드에 실패했습니다.");
    }
  }

  public FileResponse saveImage(FileSaveRequest reqData) {
    if (reqData == null || reqData.getFile() == null) {
      throw new BizException("업로드할 이미지 파일이 없습니다.");
    }

    String uuid = UUID.randomUUID().toString().replace("-", "");
    String contentType = reqData.getFile().getContentType();
    String originalFilename = reqData.getFile().getOriginalFilename();
    Long fileSize = reqData.getFile().getSize();
    String fileName = FilenameUtils.getBaseName(originalFilename);
    String fileExt = FilenameUtils.getExtension(originalFilename);

    if (StringUtils.isBlank(fileName)) {
      throw new BizException("파일명이 없는 이미지 파일은 업로드 할 수 없습니다.");
    }

    if (StringUtils.isBlank(fileExt)) {
      throw new BizException("파일확장자가 없는 이미지 파일은 업로드 할 수 없습니다.");
    }

    if (fileSize == 0) {
      throw new BizException("파일 사이즈가 0인 이미지 파일은 업로드 할 수 없습니다.");
    }

    // TODO apache tika활용해서 확장자 위변조 체크
    List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/svg+xml");
    List<String> allowedExts = Arrays.asList("jpeg", "png", "gif", "svg");

    if (!allowedMimeTypes.contains(contentType) || !allowedExts.contains(fileExt)) {
      throw new BizException("이미지 파일만 업로드 할 수 있습니다.");
    }

    BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid).setContentType(contentType).build();

    try {
      storage.create(blobInfo, reqData.getFile().getBytes());
    } catch (IOException e) {
      throw new BizException("파일 업로드에 실패했습니다.");
    }

    FileEntity fileEntity = fileRepository.save(
        FileEntity.builder().orginlFileNm(fileName).orginlFileExtsnNm(fileExt).streFileNm(uuid).cntntsTyCn(contentType).fileCpcty(fileSize).build());

    return FileResponse.builder().fileId(fileEntity.getFileSn()).fileName(originalFilename).fileSize(fileSize).build();
  }

  public ResponseEntity<byte[]> getImage(Integer fileId) {
    FileEntity fileEntity = fileRepository.findById(fileId).orElse(null);

    if (fileEntity == null) {
      throw new BizException("이미지가 없습니다.");
    }

    MediaType mediaType;

    try {
      mediaType = MediaType.parseMediaType(fileEntity.getCntntsTyCn());
    } catch (InvalidMediaTypeException e) {
      mediaType = MediaType.APPLICATION_OCTET_STREAM;
    }

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

      return ResponseEntity.ok().contentType(mediaType).body(fileContent);
    } catch (IOException e) {
      throw new BizException("파일 다운로드에 실패했습니다.");
    }
  }

  public void removeFile(FileRemoveRequest reqData) {
    FileEntity fileEntity = fileRepository.findById(reqData.getFileId()).orElse(null);

    if (fileEntity == null) {
      throw new BizException("삭제할 파일이 없습니다.");
    }

    storage.delete(bucketName, fileEntity.getStreFileNm());

    fileRepository.delete(fileEntity);
  }

  public List<FileResponse> getFileList(FileListSrchRequest reqData) {
    if (CollectionUtils.isEmpty(reqData.getFileIds())) {
      throw new BizException("파일ID는 필수 값 입니다.");
    }

    List<FileEntity> fileEntityList = fileRepository.findAllById(reqData.getFileIds());

    if (CollectionUtils.isEmpty(fileEntityList)) {
      throw new BizException("파일이 없습니다.");
    }

    return fileEntityList.stream().map(fileEntity -> FileResponse.builder().fileId(fileEntity.getFileSn())
        .fileName(fileEntity.getOrginlFileNm() + "." + fileEntity.getOrginlFileExtsnNm()).fileSize(fileEntity.getFileCpcty()).build()).toList();
  }
}
