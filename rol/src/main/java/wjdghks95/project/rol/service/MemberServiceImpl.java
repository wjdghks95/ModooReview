package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjdghks95.project.rol.domain.dto.MemberDto;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberDto memberDto) {

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .phone(memberDto.getPhone())
                .name(memberDto.getName())
                .nickname(memberDto.getNickname())
                .zipcode(memberDto.getZipcode())
                .address(memberDto.getAddress())
                .detailAddress(memberDto.getDetailAddress())
                .profileImage(null)
                .role("USER")
                .build();

        memberRepository.save(member);
        return member.getId();
    }
}
