package com.io.rol.service.impl;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.domain.entity.Member;
import com.io.rol.respository.MemberRepository;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Override
    public Long join(MemberDto memberDto) {
        Member member = Member.builder()
                .phone(memberDto.getPhone())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .nickname(memberDto.getNickname())
                .zipcode(memberDto.getZipcode())
                .address(memberDto.getAddress())
                .detailAddress(memberDto.getDetailAddress())
                .role("ROLE_USER")
                .build();

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }
}
