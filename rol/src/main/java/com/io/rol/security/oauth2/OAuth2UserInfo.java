package com.io.rol.security.oauth2;


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
