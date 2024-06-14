package com.playground.api.member.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.common.net.HttpHeaders;
import com.playground.api.member.entity.MberEntity;
import com.playground.api.member.entity.specification.MemberSpecification;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.member.model.MberSrchResponse;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.repository.MberRepository;
import com.playground.api.sample.service.RedisService;
import com.playground.constants.CacheType;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
import com.playground.model.LoginMemberDto;
import com.playground.utils.CryptoUtil;
import com.playground.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MberService {
  private final MemberSpecification memberSpecification;
  private final MberRepository mberRepository;
  private final ModelMapper modelMapper;
  private final RedisService redisService;

  @Transactional
  public SignUpResponse addMber(SignUpRequest req) {
    log.debug(">>> req : {}", req);
    MberEntity rstMember = mberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());

    if (!ObjectUtils.isEmpty(rstMember)) {
      throw new BizException(MessageCode.DUPLICATE_USER);
    }

    log.debug(">>> rstMember : {}", rstMember);
    MberEntity saveMember = mberRepository.save(MberEntity.builder().mberId(req.getMberId())
        .mberPassword(CryptoUtil.encodePassword(req.getMberPassword())).mberNm(req.getMberNm()).mberEmailAdres(req.getMberEmailAdres())
        .mberBymd(req.getMberBymd()).mberSexdstnCode(req.getMberSexdstnCode()).mberTelno(req.getMberTelno()).build());

    return modelMapper.map(saveMember, SignUpResponse.class);
  }

  @Transactional(readOnly = true)
  public SignInResponse signIn(SignInRequest req) {
    MberEntity rstMember = mberRepository.findById(req.getMberId()).orElseThrow(() -> new BizException(MessageCode.INVALID_USER));

    if (!CryptoUtil.comparePassword(req.getMberPassword(), rstMember.getMberPassword())) {
      throw new BizException(MessageCode.INVALID_PASSWD);
    }

    log.debug(">>> rstMember : {}", rstMember);
    
    // 토큰 발급 및 로그인 처리
    SignInResponse signRes = SignInResponse.builder().token(JwtTokenUtil.createToken(rstMember.getMberId(), rstMember.getMberNm())).mberId(rstMember.getMberId())
        .build();
    
    // 토큰 유효여부 확인 후 securityContext 등록
    String token = signRes.getToken();
    
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    
    if (Boolean.TRUE.equals(JwtTokenUtil.isValidToken(token))) {
      LoginMemberDto userDto = LoginMemberDto.builder().mberId(rstMember.getMberId()).mberNm(rstMember.getMberNm()).build();

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(userDto, null, List.of(new SimpleGrantedAuthority("USER")));

      usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    
    return signRes;
  }
  
  @Transactional(readOnly = true)
  public void signOut(String token) {
    //refresh Token expired 시간 1초로
  }

  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "members", key = "#token", unless = "#result == null")
  @Transactional(readOnly = true)
  public MberInfoResponse getMyInfo(String token) {
    if (!ObjectUtils.isEmpty(token)) {
      MberInfoResponse member = JwtTokenUtil.autholriztionCheckUser(token); // 넘겨받은 토큰 값으로 토큰에 있는 값 꺼내기

      log.debug("szs/me : {}", member);

      MberEntity memberEntity = mberRepository.findById(member.getMberId()).orElseThrow(() -> new BizException(MessageCode.INVALID_USER)); // 토큰 claims에 담겨 있는 userId로 회원 정보 조회

      return MberInfoResponse.builder().mberId(memberEntity.getMberId()).mberNm(memberEntity.getMberNm())
          .mberEmailAdres(memberEntity.getMberEmailAdres()).build();
    } else {
      throw new BizException(MessageCode.INVALID_TOKEN);
    }
  }
  
  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "members", key = "#token", unless = "#result == null")
  @Transactional(readOnly = true)
  public MberInfoResponse getMember() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    
    return getMyInfo(token);
  }

  @Transactional(readOnly = true)
  public List<MberSrchResponse> getMberList(MberSrchRequest req) {
    MberEntity pgEntity = MberEntity.builder().mberId(req.getMberId()).mberNm(req.getMberNm()).build();

    log.debug(">>> pgEntity : {}", pgEntity);

    List<MberEntity> member = mberRepository.findAll(memberSpecification.searchCondition(pgEntity));

    log.debug(">>> member : {}", member);

    return member.stream().map(item -> modelMapper.map(item, MberSrchResponse.class)).toList();
  }

  @Transactional(readOnly = true)
  public String getMberDupCeck(MberSrchRequest req) {
    MberEntity rstMember = mberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());

    return ObjectUtils.isEmpty(rstMember) ? "N" : "Y";
  }

}
