package com.io.rol.member.controller;

import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.service.MemberService;
import com.io.rol.security.context.MemberContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    MemberService memberService;

    private static final String SMS_URL = "/api/SMS";
    private static final String FOLLOW_URL = "/api/follow";
    private static final String PHONE_NUMBER = "01027999308";

    private void clear(){
        em.flush();
        em.clear();
    }

    /*
       SMS 인증번호 발송
          인증번호 발송 버튼 클릭시 /api/SMS 로 전달
          파라미터로 휴대폰 번호를 같이 전달
          4자리의 임의의 수 반환
     */
    @Test
    @DisplayName("SMS 인증번호 발송")
    void sendSms() throws Exception {
        // given
        mockMvc.perform(get(SMS_URL)
                        .accept(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("phoneNum", PHONE_NUMBER))
                // when, then
                .andExpect(status().isOk())
                .andDo(print());
    }

    /*
       팔로우
          팔로우 버튼 클릭시 /api/follow 로 전달
          파라미터로 팔로우할 회원의 id를 같이 전달
          팔로우한 회원의 팔로워수 반환
     */
    
    @BeforeEach
    void member_join() {
        MemberDto memberDto = createMemberDto("01012345678", "test@test.com", "asdf1234!",
                "이름", "테스트", "12345", "주소", "상세주소");
        Long id = memberService.join(memberDto);

        Member findMember = memberService.getMember(id);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(findMember.getRole().value()));

        MemberContext memberContext = new MemberContext(findMember, roles);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(memberContext, null, roles));
        SecurityContextHolder.setContext(securityContext);
        clear();
    }
    
    @Test
    @DisplayName("팔로우")
    void follow() throws Exception {
        MemberDto memberDto = createMemberDto("01012345678", "test2@test.com", "asdf1234!",
                "이름", "테스트2", "12345", "주소", "상세주소");
        Long id = memberService.join(memberDto);

        // given
        mockMvc.perform(get(FOLLOW_URL)
                        .accept(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(id)))
                // when, then
                .andExpect(status().isOk())
                .andDo(print());
    }

    private MemberDto createMemberDto(String phone, String email, String password, String name, String nickname,
                                      String zipcode, String address, String detailAddress) {
        return MemberDto.builder()
                .phone(phone)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .zipcode(zipcode)
                .address(address)
                .detailAddress(detailAddress)
                .build();
    }
}