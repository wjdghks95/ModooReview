package com.io.rol.security.service;

import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.repository.MemberRepository;
import com.io.rol.security.context.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Form 로그인 사용자 정보 조회 서비스
@Service
@RequiredArgsConstructor
public class FormUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자가 입력한 아이디(이메일)로 해당 Member를 저장소에서 찾음
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().value()));

        MemberContext memberContext = new MemberContext(member, roles); // UserDetails 생성

        return memberContext;
    }
}
