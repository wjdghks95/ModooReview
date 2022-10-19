package wjdghks95.project.rol.security.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.security.oauth.GoogleUserInfo;
import wjdghks95.project.rol.security.oauth.KakaoUserInfo;
import wjdghks95.project.rol.security.oauth.NaverUserInfo;
import wjdghks95.project.rol.security.oauth.OAuth2UserInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); // DefaultOAuth2UserService 를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // User 정보를 가지고옴

        OAuth2UserInfo oAuth2UserInfo = null; // OAuth 로그인시 회원정보를 가져오는 인터페이스

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) { // 구글 로그인 요청
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) { // 네이버 로그인 요청
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) { // 카카오 로그인 요청
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String phone = oAuth2UserInfo.getPhone().replaceAll("[^0-9]", "");
        String email = oAuth2UserInfo.getEmail();
        String password = passwordEncoder.encode("OAuth2LoginPassword");
        String name = oAuth2UserInfo.getName();
        String nickname = oAuth2UserInfo.getNickname();
        String zipcode = oAuth2UserInfo.getZipcode();
        String address = oAuth2UserInfo.getAddress();
        String detailAddress = oAuth2UserInfo.getDetailAddress();
        String picture = oAuth2UserInfo.getPicture();

        Member member = memberRepository.findByEmail(email).orElseGet(() -> {

            Member newMember = Member.builder()
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .name(name)
                    .nickname(nickname != null ? nickname : "닉네임" + UUID.randomUUID().toString().substring(0, 4))
                    .zipcode(zipcode)
                    .address(address)
                    .detailAddress(detailAddress)
                    .profileImage(picture != null ? picture : null)
                    .role("USER")
                    .build();
            return memberRepository.save(newMember);
        });

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole()));

        return new MemberContext(member, roles, oAuth2User.getAttributes());
    }
}
