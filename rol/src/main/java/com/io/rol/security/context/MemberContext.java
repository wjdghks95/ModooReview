package com.io.rol.security.context;

import com.io.rol.member.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// 회원의 권한과 정보를 담는 인증 객체
@Getter
public class MemberContext implements UserDetails, OAuth2User {

    private Member member;
    private List<GrantedAuthority> roles;
    private Map<String, Object> attributes;

    public MemberContext(Member member, List<GrantedAuthority> roles) {
        this.member = member;
        this.roles = roles;
    }

    public MemberContext(Member member, List<GrantedAuthority> roles, Map<String, Object> attributes) {
        this.member = member;
        this.roles = roles;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 계정의 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // 계정의 비밀번호
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 계정의 고유한 값
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    // 계정의 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정의 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정의 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 고유한 값
    @Override
    public String getName() {
        return member.getEmail();
    }
}
