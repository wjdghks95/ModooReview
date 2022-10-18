package wjdghks95.project.rol.security.oauth;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getPhone();
    String getEmail();
    String getName();
    String getNickname();
    String getZipcode();
    String getAddress();
    String getDetailAddress();
    String getPicture();

}
