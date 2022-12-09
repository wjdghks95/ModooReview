package com.io.rol.security.config;

import com.io.rol.security.handler.FormAuthenticationFailureHandler;
import com.io.rol.security.service.FormRememberMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final FormAuthenticationFailureHandler formAuthenticationFailureHandler;
    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    /**
     * AuthenticationManagerBuilder: 인증 객체를 만들 수 있는 API 제공
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.authenticationProvider(authenticationProvider); // FormAuthenticationProvider 등록
        return auth.build();
    }

    /**
     *  HttpSecurity: 세부적인 보안 기능을 설정할 수 있는 API 제공
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable(); // httpBasic 인증방법 비활성화

        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/signUp/**", "/logout").permitAll()
                .antMatchers("/contents/board/new").hasAnyAuthority("ROLE_USER")
                .antMatchers("/contents/**").permitAll()
                .anyRequest().authenticated()

                /**
                 * Form Login
                 */
                .and()
                .formLogin()
                .loginPage("/login") // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
                .failureHandler(formAuthenticationFailureHandler) // 로그인 실패 핸들러

                /**
                 * Logout
                 */
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // get 방식의 로그아웃 사용시 URL 정의
                .deleteCookies("remember-me", "JSESSIONID") // 로그아웃 후 쿠키 삭제
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지

                /**
                 * Remember-me
                 */
                .and()
                .rememberMe()
                .tokenValiditySeconds(3600) // 쿠키 만료 시간 (Default 14일)
                .rememberMeServices(rememberMeServices(tokenRepository())); // PersistentTokenBasedRememberMeServices (DB 저장 방식) 등록

        return http.build();
    }

    /**
     * WebSecurity: 보안이 적용되지 않도록 할 수 있도록 하는 API 제공
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .and()
                .ignoring()
                .mvcMatchers("/css/**", "/js/**", "/icon/**", "/font/**", "/img/**", "/resources/**", "/error")
                .antMatchers("/h2-console/**", "/favicon.ico");
    }

    /** 사용자 정의 RememberMeServices */
    public RememberMeServices rememberMeServices(PersistentTokenRepository tokenRepository) {
        return new FormRememberMeService("rememberMeKey", userDetailsService, tokenRepository);
    }

    /** PersistentTokenBasedRememberMeServices 를 위한 저장소 */
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // DataSource 설정
        return jdbcTokenRepository;
    }
}
