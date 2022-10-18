package wjdghks95.project.rol.security.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import wjdghks95.project.rol.domain.entity.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class MemberContext implements UserDetails, OAuth2User {

    private Member member;
    private List<GrantedAuthority> roles;
    private Map<String, Object> attributes;

    public MemberContext(Member member, List<GrantedAuthority> roles) {
        this.member = member;
        this.roles = roles;
    }

    public MemberContext(Member member, List<GrantedAuthority> roles, Map<String, Object> attributes) {
        this.member = member;
        this.roles = roles;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getName() {
        return null;
    }
}
