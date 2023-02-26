package com.io.rol.security.oauth2;

import java.util.Map;

// 구글
public class GoogleUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getNickname() {
        return null;
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
        return (String) attributes.get("picture");
    }
}
