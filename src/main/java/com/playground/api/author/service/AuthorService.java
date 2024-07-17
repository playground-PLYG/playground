package com.playground.api.author.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.author.entity.AuthorEntity;
import com.playground.api.author.entity.AuthorMenuEntity;
import com.playground.api.author.entity.MberAuthorEntity;
import com.playground.api.author.model.AuthorMenuRequest;
import com.playground.api.author.model.AuthorMenuResponse;
import com.playground.api.author.model.AuthorRequest;
import com.playground.api.author.model.AuthorResponse;
import com.playground.api.author.model.MberAuthorRequest;
import com.playground.api.author.model.MberAuthorResponse;
import com.playground.api.author.repository.AuthorMenuRepository;
import com.playground.api.author.repository.AuthorRepository;
import com.playground.api.author.repository.MberAuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final MberAuthorRepository mberAuthorRepository;
  private final AuthorMenuRepository authorMenuRepository;
  private final ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<AuthorResponse> getAuthorList(AuthorRequest req) {
    return authorRepository.getAuthorList(req.getAuthorId(), req.getAuthorNm(), req.getDeleteAt());
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public AuthorResponse addAuthor(AuthorRequest req) {
    AuthorEntity saveEntity = AuthorEntity.builder().authorId(req.getAuthorId()).authorNm(req.getAuthorNm()).deleteAt(req.getDeleteAt()).build();

    AuthorEntity result = authorRepository.save(saveEntity);

    return AuthorResponse.builder().authorId(result.getAuthorId()).authorNm(result.getAuthorNm()).deleteAt(result.getDeleteAt()).build();
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public AuthorResponse modifyAuthor(AuthorRequest req) {
    AuthorEntity updateEntity = AuthorEntity.builder().authorId(req.getAuthorId()).authorNm(req.getAuthorNm()).deleteAt(req.getDeleteAt()).build();

    AuthorEntity result = authorRepository.save(updateEntity);

    return AuthorResponse.builder().authorId(result.getAuthorId()).authorNm(result.getAuthorNm()).build();
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public AuthorResponse removeAuthor(AuthorRequest req) {
    AuthorEntity deleteEntity = AuthorEntity.builder().deleteAt(req.getDeleteAt()).build();

    AuthorEntity result = authorRepository.save(deleteEntity);

    return AuthorResponse.builder().deleteAt(result.getDeleteAt()).build();
  }

  @Transactional(readOnly = true)
  public List<MberAuthorResponse> getMberAuthorList(MberAuthorRequest req) {
    List<MberAuthorResponse> resList = mberAuthorRepository.getMberAuthorList(req.getMberId());

    return resList.stream().map(item -> modelMapper.map(item, MberAuthorResponse.class)).toList();
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public MberAuthorResponse addMberAuthor(MberAuthorRequest req) {
    MberAuthorEntity saveEntity = MberAuthorEntity.builder().mberId(req.getMberId()).authorId(req.getAuthorId()).build();

    MberAuthorEntity result = mberAuthorRepository.save(saveEntity);

    return MberAuthorResponse.builder().mberId(result.getMberId()).authorId(result.getAuthorId()).build();
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public void removeMberAuthor(List<MberAuthorRequest> req) {
    for (int i = 0; i < req.size(); i++) {
      String mberId = req.get(i).getMberId();
      String authorId = req.get(i).getAuthorId();

      mberAuthorRepository.delete(MberAuthorEntity.builder().mberId(mberId).authorId(authorId).build());
    }
  }

  @Transactional(readOnly = true)
  public List<AuthorMenuResponse> getAuthorMenuList(AuthorMenuRequest req) {
    List<AuthorMenuResponse> resList = authorMenuRepository.getAuthorMenuList(req.getAuthorId());

    return resList.stream().map(item -> modelMapper.map(item, AuthorMenuResponse.class)).toList();
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public AuthorMenuResponse addAuthorMenu(AuthorMenuRequest req) {
    AuthorMenuEntity saveEntity = AuthorMenuEntity.builder().authorId(req.getAuthorId()).menuSn(req.getMenuSn()).build();

    AuthorMenuEntity result = authorMenuRepository.save(saveEntity);

    return AuthorMenuResponse.builder().authorId(result.getAuthorId()).menuSn(result.getMenuSn()).build();
  }

  @CacheEvict(cacheNames = {"menus"}, allEntries = true)
  @Transactional
  public void removeAuthorMenu(List<AuthorMenuRequest> req) {
    for (int i = 0; i < req.size(); i++) {
      String authorId = req.get(i).getAuthorId();
      Integer menuSn = req.get(i).getMenuSn();

      authorMenuRepository.delete(AuthorMenuEntity.builder().authorId(authorId).menuSn(menuSn).build());
    }
  }

}
