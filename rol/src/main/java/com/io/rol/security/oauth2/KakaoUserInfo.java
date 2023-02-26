package com.io.rol.security.oauth2;

import java.util.Map;

// 카카오
public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = getKakaoAccount();
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getNickname() {
        Map<String, Object> kakaoAccount = getKakaoAccount();
        Map<String, Object> profile = getProfile(kakaoAccount);
        return (String) profile.get("nickname");
    }

    @Override
    public String getZipcode() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getDetailAddress() {
        return null;
    }

    @Override
    public String getPicture() {
        Map<String, Object> kakaoAccount = getKakaoAccount();
        Map<String, Object> profile = getProfile(kakaoAccount);
        return (String) profile.get("profile_image_url");
    }

    private Map<String, Object> getProfile(Map<String, Object> kakaoAccount) {
        return (Map<String, Object>) kakaoAccount.get("profile");
    }

    private Map<String, Object> getKakaoAccount() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return kakaoAccount;
    }
}
