package com.io.rol.security.oauth2;

// OAuth2 로그인시 회원정보를 가져오는 인터페이스
public interface OAuth2UserInfo {
    String getPhone();
    String getEmail();
    String getName();

    String getNickname();
    String getZipcode();
    String getAddress();
    String getDetailAddress();
    String getPicture();
}
