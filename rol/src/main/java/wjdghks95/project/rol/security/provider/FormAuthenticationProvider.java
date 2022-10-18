package wjdghks95.project.rol.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wjdghks95.project.rol.security.service.FormUserDetailService;
import wjdghks95.project.rol.security.service.MemberContext;

@Component
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final FormUserDetailService formUserDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext memberContext = (MemberContext) formUserDetailService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, memberContext.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        return new UsernamePasswordAuthenticationToken(memberContext, null, memberContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
