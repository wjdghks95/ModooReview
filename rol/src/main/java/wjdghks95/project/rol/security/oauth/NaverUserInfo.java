package wjdghks95.project.rol.security.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getPhone() {
        return (String) attributes.get("mobile");
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
        return (String) attributes.get("nickname");
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
        return (String) attributes.get("profile_image");
    }
}
