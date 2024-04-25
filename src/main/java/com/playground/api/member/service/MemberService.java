package com.playground.api.member.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.member.entity.MberEntity;
import com.playground.api.member.entity.specification.MemberSpecification;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.member.model.MberSrchResponse;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.repository.MemberRepository;
import com.playground.constants.CacheType;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
import com.playground.utils.CryptoUtil;
import com.playground.utils.JwtTokenUtil;
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
  public SignUpResponse addMber(SignUpRequest req) {
    log.debug(">>> req : {}", req);
    MberEntity rstMember = memberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());

    if (!ObjectUtils.isEmpty(rstMember)) {
      throw new BizException(MessageCode.DUPLICATE_USER);
    }

    log.debug(">>> rstMember : {}", rstMember);
    MberEntity saveMember = memberRepository.save(MberEntity.builder().mberId(req.getMberId())
        .mberPassword(CryptoUtil.encodePassword(req.getMberPassword())).mberNm(req.getMberNm()).mberEmailAdres(req.getMberEmailAdres())
        .mberBymd(req.getMberBymd()).mberSexdstnCode(req.getMberSexdstnCode()).mberTelno(req.getMberTelno()).build());

    return modelMapper.map(saveMember, SignUpResponse.class);
  }

  @Transactional(readOnly = true)
  public SignInResponse signIn(SignInRequest req) {
    MberEntity rstMember = memberRepository.findById(req.getMberId()).orElseThrow(() -> new BizException(MessageCode.INVALID_USER));

    if (!CryptoUtil.comparePassword(req.getMberPassword(), rstMember.getMberPassword())) {
      throw new BizException(MessageCode.INVALID_PASSWD);
    }

    log.debug(">>> rstMember : {}", rstMember);

    // 토큰 발급 및 로그인 처리
    return SignInResponse.builder().token(JwtTokenUtil.createToken(rstMember.getMberId(), rstMember.getMberNm())).build();
  }

  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "members", key = "#token", unless = "#result == null")
  @Transactional(readOnly = true)
  public MberInfoResponse getMyInfo(String token) {
    if (!ObjectUtils.isEmpty(token)) {
      MberInfoResponse member = JwtTokenUtil.autholriztionCheckUser(token); // 넘겨받은 토큰 값으로 토큰에 있는 값 꺼내기

      log.debug("szs/me : {}", member);

      MberEntity memberEntity = memberRepository.findById(member.getMberId()).orElseThrow(() -> new BizException(MessageCode.INVALID_USER)); // 토큰 claims에 담겨 있는 userId로 회원 정보 조회

      return modelMapper.map(memberEntity, MberInfoResponse.class);
    } else {
      throw new BizException(MessageCode.INVALID_TOKEN);
    }
  }

  @Transactional
  public List<MberSrchResponse> getMberList(MberSrchRequest req) {
    MberEntity pgEntity = MberEntity.builder().mberId(req.getMberId()).mberNm(req.getMberNm()).build();

    log.debug(">>> pgEntity : {}", pgEntity);

    List<MberEntity> member = memberRepository.findAll(memberSpecification.searchCondition(pgEntity));

    log.debug(">>> member : {}", member);

    return member.stream().map(item -> modelMapper.map(item, MberSrchResponse.class)).toList();
  }

  @Transactional
  public String getMberDupCeck(MberSrchRequest req) {
    MberEntity rstMember = memberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());

    return ObjectUtils.isEmpty(rstMember) ? "N" : "Y";
  }

}
