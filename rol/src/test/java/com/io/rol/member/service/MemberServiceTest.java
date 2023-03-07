package com.io.rol.member.service;

import com.io.rol.domain.Role;
import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    private void clear(){
        em.flush();
        em.clear();
    }

    /*
       회원가입
          회원가입 시 핸드폰 번호, 이메일, 비밀번호, 이름, 닉네임, 주소를 입력하지 않으면 오류
          이미 존재하는 아이디, 닉네임이 있으면 오류
          회원가입 후 회원의 ROLE 은 USER
     */
    @Test
    @DisplayName("회원가입_성공")
    void join_success() {
        // given
        MemberDto memberDto = createMemberDto();

        // when
        Long joinMemberId = memberService.join(memberDto);
        clear();

        // then
        Member member = memberRepository.findById(joinMemberId).orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException"));
        assertNotNull(member);
        assertEquals(joinMemberId, member.getId());
        assertEquals(memberDto.getPhone(), member.getPhone());
        assertEquals(memberDto.getEmail(), member.getEmail());
        assertTrue(passwordEncoder.matches(memberDto.getPassword(), member.getPassword()));
        assertEquals(memberDto.getName(), member.getName());
        assertEquals(memberDto.getNickname(), member.getNickname());
        assertEquals(memberDto.getZipcode(), member.getZipcode());
        assertEquals(memberDto.getAddress(), member.getAddress());
        assertEquals(memberDto.getDetailAddress(), member.getDetailAddress());
        assertEquals(member.getRole(), Role.USER);
    }

    @Test
    @DisplayName("회원가입_실패_이메일, 닉네임 중복")
    void join_failure_duplication_emailAndNickname() {
        // given
        MemberDto memberDto = createMemberDto();
        memberService.join(memberDto);
        clear();

        // when, then
        assertThrows(Exception.class, () -> memberService.join(memberDto));
    }

    @Test
    @DisplayName("회원가입_실패_입력하지 않은 필드 존재")
    void join_failure_empty_field() {
        // given
        MemberDto memberDto1 = new MemberDto(null, "test@test.com", "asdf1234!",
                "이름", "테스트", "12345", "경기도 고양시 현중로 10", "101동 101호");
        MemberDto memberDto2 = new MemberDto("01012345678", null, "asdf1234!",
                "이름", "테스트", "12345", "경기도 고양시 현중로 10", "101동 101호");
        MemberDto memberDto3 = new MemberDto("01012345678", "test@test.com", null,
                "이름", "테스트", "12345", "경기도 고양시 현중로 10", "101동 101호");
        MemberDto memberDto4 = new MemberDto("01012345678", "test@test.com", "asdf1234!",
                null, "테스트", "12345", "경기도 고양시 현중로 10", "101동 101호");
        MemberDto memberDto5 = new MemberDto("01012345678", "test@test.com", "asdf1234!",
                "이름", null, "12345", "경기도 고양시 현중로 10", "101동 101호");
        MemberDto memberDto6 = new MemberDto("01012345678", "test@test.com", "asdf1234!",
                "이름", "테스트", null, null, null);

        // when, then
        assertThrows(Exception.class, () -> memberService.join(memberDto1));
        assertThrows(Exception.class, () -> memberService.join(memberDto2));
        assertThrows(Exception.class, () -> memberService.join(memberDto3));
        assertThrows(Exception.class, () -> memberService.join(memberDto4));
        assertThrows(Exception.class, () -> memberService.join(memberDto5));
        assertThrows(Exception.class, () -> memberService.join(memberDto6));
    }

    private MemberDto createMemberDto() {
        return MemberDto.builder()
                .phone("01012345678")
                .email("test@test.com")
                .password("asdf1234!")
                .name("이름")
                .nickname("테스트")
                .zipcode("12345")
                .address("주소")
                .detailAddress("상세주소")
                .build();
    }
}