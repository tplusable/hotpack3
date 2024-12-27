package com.table.hotpack3.service;

import com.table.hotpack3.domain.User;
import com.table.hotpack3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
// spring security에서 사용자 정보를 가져오는 interface
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // username(email)로 사용자 정보 가져옴
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException((email)));
    }
}
