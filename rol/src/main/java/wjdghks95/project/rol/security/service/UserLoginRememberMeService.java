package wjdghks95.project.rol.security.service;

import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Date;

public class UserLoginRememberMeService extends AbstractRememberMeServices {

    PersistentTokenRepository tokenRepository;

    private SecureRandom random; // token값 신규 생성을 위한 랜덤 넘버 생성 객체

    private String key;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public UserLoginRememberMeService(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService);
        this.key = key;
        random = new SecureRandom();
        this.tokenRepository = tokenRepository;
    }

    /** 첫 로그인 시 쿠키 발행 및 토큰정보 DB 업데이트 */
    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {

        MemberContext principal = (MemberContext) successfulAuthentication.getPrincipal();
        String username = principal.getMember().getEmail();
        String newSeriesValue = generateTokenValue();
        String newTokenValue = generateTokenValue();

        // 토큰 발행
        PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, newSeriesValue, newTokenValue, new Date());

        try {
            this.tokenRepository.createNewToken(persistentToken); // 토큰 저장

            String[] rawCookieValues = new String[] { newSeriesValue, newTokenValue };
            super.setCookie(rawCookieValues, getTokenValiditySeconds(), request, response); // 쿠키 발행
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 자동 로그인 로직 - 쿠키 유효성 검증 및 사용자 정보 객체 리턴 */
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
        // 쿠키 : series, token
        // 포함된 값이 2개가 아닌 경우
        if (cookieTokens.length != 2) {
            throw new RememberMeAuthenticationException("잘못된 쿠키");
        }

        String cookieSeries = cookieTokens[0];
        String cookieToken = cookieTokens[1];

        PersistentRememberMeToken token = this.tokenRepository.getTokenForSeries(cookieSeries); // DB 토큰 정보 확인

        // DB에 정보가 없을 경우
        if (token == null) {
            throw new RememberMeAuthenticationException("존재하지 않는 series");
        }

        // DB에 series는 있는데 Token 값이 같지 않을 경우
        if (!cookieToken.equals(token.getTokenValue())) {

            // DB에서 해당 데이터 삭제
            this.tokenRepository.removeUserTokens(token.getUsername());
            throw new CookieTheftException("변조된 쿠키 발견");
        }

        // 유효기간 검증
        if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System.currentTimeMillis()) {

            // DB에서 해당 데이터 삭제
            this.tokenRepository.removeUserTokens(token.getUsername());
            throw new RememberMeAuthenticationException("유효기간 만료 쿠키");
        }


        String newTokenValue = generateTokenValue();
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(), newTokenValue, new Date());
        try {
            this.tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate()); // 신규 token 값으로 업데이트

            String[] rawCookieValues = new String[] { cookieSeries, newTokenValue };
            super.setCookie(rawCookieValues, getTokenValiditySeconds(), request, response); // 변경된 token 값으로 새로운 쿠키 발행
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RememberMeAuthenticationException("새로운 token DB 업데이트 실패");
        }
        return getUserDetailsService().loadUserByUsername(token.getUsername());
    }

    /** 자동 로그인 성공시 RememberMeAuthenticationToken 을 생성하여 반환 */
    @Override
    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
        MemberContext memberContext = (MemberContext) user;
        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(this.key, memberContext, memberContext.getAuthorities());
        auth.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return auth;
    }

    /** 로그아웃 시 쿠키, DB 정보 삭제 */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        super.logout(request, response, authentication);
        if (authentication != null) {
            MemberContext principal = (MemberContext) authentication.getPrincipal();
            this.tokenRepository.removeUserTokens(principal.getMember().getEmail());
        }
    }

    /** Series, Token 랜덤값으로 생성후 인코딩 */
    private String generateTokenValue() {
        byte[] newToken = new byte[16];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }
}
