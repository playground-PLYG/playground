package com.playground.api.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.playground.api.member.entity.MberEntity;
import com.playground.api.member.repository.MberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

  private final MberRepository mberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return mberRepository.findByMberId(username)
              .map(this::createUserDetails)
              .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
  }

  // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
  private UserDetails createUserDetails(MberEntity users) {
      return new User(users.getMberId(), users.getMberPassword(), users.getAuthorities());
  }
}
