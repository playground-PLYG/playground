package com.playground.api.member.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.member.entity.MemberEntity;
import com.playground.api.member.entity.specification.MemberSpecification;
import com.playground.api.member.model.GetEmailResponse;
import com.playground.api.member.model.MemberInfoResponse;
import com.playground.api.member.model.MemberResponse;
import com.playground.api.member.model.MemberSearchRequest;
import com.playground.api.member.model.PgSignUpRequest;
import com.playground.api.member.model.PgSignUpResponse;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.repository.MemberRepository;
import com.playground.constants.CacheType;
import com.playground.exception.CustomException;
import com.playground.utils.CryptoUtil;
import com.playground.utils.JwtTokenUtil;
import com.playground.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberSpecification memberSpecification;
  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;


  @Transactional
  public SignUpResponse signUp(SignUpRequest req) {
    log.debug(">>> req : {}", req);
    MemberEntity rstMember = memberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());

    if (!ObjectUtils.isEmpty(rstMember)) {
      throw new CustomException(MessageUtils.DUPLICATE_USER);
    }
    
    MemberEntity saveMember = memberRepository.save(MemberEntity.builder()
        .mberId(req.getMberId())
        .mberPassword(CryptoUtil.encodePassword(req.getMberPassword()))
        .mberNm(req.getMberNm())
        .mberEmailAdres(req.getMberEmailAdres())
        .build());

    return modelMapper.map(saveMember, SignUpResponse.class);
  }

  @Transactional(readOnly = true)
  public SignInResponse signIn(SignInRequest req) {
    MemberEntity rstMember = memberRepository.findById(req.getMberId()).orElseThrow(() -> new CustomException(MessageUtils.INVALID_USER));

    if (!CryptoUtil.comparePassword(req.getMberPassword(), rstMember.getMberPassword())) {
      throw new CustomException(MessageUtils.INVALID_PASSWD);
    }

    log.debug(">>> rstMember : {}", rstMember);

    // 토큰 발급 및 로그인 처리
    return SignInResponse.builder().token(JwtTokenUtil.createToken(rstMember.getMberId(), rstMember.getMberNm())).build();
  }

  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "members", key = "#token", unless = "#result == null")
  @Transactional(readOnly = true)
  public MemberInfoResponse myInfo(String token) {
    if (!ObjectUtils.isEmpty(token)) {
      MemberInfoResponse member = JwtTokenUtil.autholriztionCheckUser(token); // 넘겨받은 토큰 값으로 토큰에 있는 값 꺼내기

      log.debug("szs/me : {}", member);

      MemberEntity memberEntity = memberRepository.findById(member.getMberId()).orElseThrow(() -> new CustomException(MessageUtils.INVALID_USER)); // 토큰 claims에 담겨 있는 userId로 회원 정보 조회

      return modelMapper.map(memberEntity, MemberInfoResponse.class);
    } else {
      throw new CustomException(MessageUtils.INVALID_TOKEN);
    }
  }

  @Transactional(readOnly = true)
  public GetEmailResponse getEmail(String email) {
    MemberEntity member = memberRepository.findByMberEmailAdres(email);

    if (member == null) {
      member = new MemberEntity();
    }

    return modelMapper.map(member, GetEmailResponse.class);
  }

  @Transactional
  public PgSignUpResponse pgSignUp(PgSignUpRequest req) {

    MemberEntity member = memberRepository.save(MemberEntity.builder().mberNm(req.getMberNm()).mberBymd(req.getMberBymd())
        .mberEmailAdres(req.getMberEmailAdres()).mberSexdstnCode(req.getMberSexdstnCode()).mberTelno(req.getMberTelno()).build());

    return modelMapper.map(member, PgSignUpResponse.class);
  }

  @Transactional
  public List<MemberResponse> getMemeberList(MemberSearchRequest req) {

    MemberEntity pgEntity = modelMapper.map(req, MemberEntity.class);

    log.debug(">>> pgEntity : {}", pgEntity);

    List<MemberEntity> member = memberRepository.findAll(memberSpecification.searchCondition(pgEntity));

    log.debug(">>> member : {}", member);

    return member.stream().map(item -> modelMapper.map(item, MemberResponse.class)).toList();
  }
  
  @Transactional
  public String getMemberDupCheck(MemberSearchRequest req) {
    MemberEntity rstMember = memberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());
    
    return ObjectUtils.isEmpty(rstMember) ? "N" : "Y";
  }

}

