package com.io.rol.security.provider;

import com.io.rol.security.context.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); //사용자가 입력한 아이디(이메일)
        String password = (String) authentication.getCredentials(); // 사용자가 입력한 패스워드

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(username); // UserDetails 반환

        if (!passwordEncoder.matches(password, memberContext.getPassword())) { // 패스워드 검증
            throw new BadCredentialsException("BadCredentialsException");
        }

        // AuthenticationManager 에 UsernamePasswordAuthenticationToken 전달
        return new UsernamePasswordAuthenticationToken(memberContext, null, memberContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
