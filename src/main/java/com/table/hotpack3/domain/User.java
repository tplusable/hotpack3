package com.table.hotpack3.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name="users_3")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", updatable=false)
    private Long userId;

    @Column(name="email", nullable=false, unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Builder
    public User(String email, String password, String auth) {
        this.email=email;
        this.password=password;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // id 반환
    @Override
    public String getUsername() {
        return email;
    }

    // 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // 계정 잠금여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // 패스워드 만료여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // 계정 사용가능여부 반환
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
