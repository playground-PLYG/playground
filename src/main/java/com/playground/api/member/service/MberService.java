package com.playground.api.member.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.common.net.HttpHeaders;
import com.playground.api.member.entity.MberEntity;
import com.playground.api.member.entity.specification.MemberSpecification;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberModifyInfoRequest;
import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.member.model.MberSrchResponse;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.model.TokenRequest;
import com.playground.api.member.repository.MberRepository;
import com.playground.api.sample.service.RedisService;
import com.playground.constants.CacheType;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
import com.playground.utils.CryptoUtil;
import com.playground.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MberService {
  private final MemberSpecification memberSpecification;
  private final MberRepository mberRepository;
  private final ModelMapper modelMapper;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final RedisTemplate<String, String> redisTemplate;
  private final JwtTokenUtil jwtTokenUtil;

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

    log.debug(">>> rstMember : {}", rstMember);
    
    // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
    // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getMberId(),req.getMberPassword());

    // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
    // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 3. 인증 정보를 기반으로 JWT 토큰 생성
    SignInResponse tokenInfo = jwtTokenUtil.generateToken(authentication, rstMember.getMberNm());

    // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
    // 추후 타입 설정 필요
    redisTemplate.opsForValue()
            .set("RefreshToken:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
    
    return tokenInfo;
  }

  @Transactional(readOnly = true)
  public void signOut(String token) {
    
   // 1. Access Token 검증
    if (!jwtTokenUtil.validateToken(token)) {
        throw new BizException("잘못된 요청입니다.");
    }

    // 2. Access Token 에서 User id 을 가져옵니다.
    Authentication authentication = jwtTokenUtil.getAuthentication(token);

    // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
    if (redisTemplate.opsForValue().get("RefreshToken:" + authentication.getName()) != null) {
        // Refresh Token 삭제
        redisTemplate.delete("RefreshToken:" + authentication.getName());
    }

    // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
    Long expiration = jwtTokenUtil.getExpiration(token);
    redisTemplate.opsForValue()
            .set(token, "logout", expiration, TimeUnit.MILLISECONDS);

  }
  
  public SignInResponse reissue(TokenRequest reissue) {
    // 1. Refresh Token 검증
    if (!jwtTokenUtil.validateToken(reissue.getRefreshToken())) {
        throw new BizException("Refresh Token 정보가 유효하지 않습니다.");
    }

    // 2. Access Token 에서 User id 을 가져옵니다.
    Authentication authentication = jwtTokenUtil.getAuthentication(reissue.getAccessToken());

    // 3. Redis 에서 User id를 기반으로 저장된 Refresh Token 값을 가져옵니다.
    String refreshToken = (String)redisTemplate.opsForValue().get("RefreshToken:" + authentication.getName());

    //(추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
    if(ObjectUtils.isEmpty(refreshToken)) {
        throw new BizException("잘못된 요청입니다.");
    }
    if(!refreshToken.equals(reissue.getRefreshToken())) {
        throw new BizException("Refresh Token 정보가 일치하지 않습니다.");
    }
    
    // 4. 새로운 토큰을 만들기 위하여 claim에서 사용자 명을 조회 후 새로운 토큰 발급
    Claims claims = jwtTokenUtil.parseClaims(reissue.getAccessToken());
    SignInResponse tokenInfo = jwtTokenUtil.generateToken(authentication, claims.get("mberNm").toString());

    // 5. RefreshToken Redis 업데이트
    redisTemplate.opsForValue()
            .set("RefreshToken:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

    return tokenInfo;
}

  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "members", key = "#token", unless = "#result == null")
  @Transactional(readOnly = true)
  public MberInfoResponse getMyInfo(String token) {
    if (!ObjectUtils.isEmpty(token)) {
      MberInfoResponse member = jwtTokenUtil.autholriztionCheckUser(token); // 넘겨받은 토큰 값으로 토큰에 있는 값 꺼내기

      log.debug("szs/me : {}", member);

      return mberRepository.findByIdDetail(member.getMberId()); // 토큰 claims에 담겨 있는 userId로 회원 정보 조회

    } else {
      throw new BizException(MessageCode.INVALID_TOKEN);
    }
  }

  @Transactional(readOnly = true)
  public MberInfoResponse getMember() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    return getMyInfo(token);
  }

  @Transactional(readOnly = true)
  public List<MberSrchResponse> getMberList(MberSrchRequest req) {
    MberEntity pgEntity = MberEntity.builder().mberId(req.getMberId()).mberNm(req.getMberNm()).build();

    List<MberEntity> member = mberRepository.findAll(memberSpecification.searchCondition(pgEntity));

    return member.stream()
        .map(entity -> MberSrchResponse.builder().mberId(entity.getMberId()).mberNm(entity.getMberNm()).mberBymd(entity.getMberBymd())
            .mberEmailAdres(entity.getMberEmailAdres()).mberSexdstnCode(entity.getMberSexdstnCode()).mberTelno(entity.getMberTelno())
            .registDt(entity.getRegistDt()).registUsrId(entity.getRegistUsrId()).updtUsrId(entity.getUpdtUsrId()).updtDt(entity.getUpdtDt()).build())
        .toList();
  }

  @Transactional(readOnly = true)
  public Page<MberSrchResponse> getMberPageList(Pageable pageable, MberSrchRequest req) {

    Page<MberEntity> memberPageList = mberRepository.getMberPageList(req.getMberId(), req.getMberNm(), pageable);

    List<MberSrchResponse> mberList = memberPageList.getContent().stream()
        .map(entity -> MberSrchResponse.builder().mberId(entity.getMberId()).mberNm(entity.getMberNm()).mberBymd(entity.getMberBymd())
            .mberEmailAdres(entity.getMberEmailAdres()).mberSexdstnCode(entity.getMberSexdstnCode()).mberTelno(entity.getMberTelno())
            .registDt(entity.getRegistDt()).registUsrId(entity.getRegistUsrId()).updtDt(entity.getUpdtDt()).updtUsrId(entity.getUpdtUsrId()).build())
        .toList();
    return new PageImpl<>(mberList, memberPageList.getPageable(), memberPageList.getTotalElements());

  }

  @Transactional(readOnly = true)
  public String getMberDupCeck(MberSrchRequest req) {
    MberEntity rstMember = mberRepository.findByMberIdOrMberEmailAdres(req.getMberId(), req.getMberEmailAdres());

    return ObjectUtils.isEmpty(rstMember) ? "N" : "Y";
  }

  @Transactional
  public void modifyMberinfo(@Valid MberModifyInfoRequest req) {
    mberRepository.updateMberinfoByMberId(req);

  }

}
