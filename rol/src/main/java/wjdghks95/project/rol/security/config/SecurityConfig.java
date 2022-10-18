package wjdghks95.project.rol.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import wjdghks95.project.rol.security.provider.FormAuthenticationProvider;
import wjdghks95.project.rol.security.service.CustomOAuth2UserService;
import wjdghks95.project.rol.security.service.FormUserDetailService;
import wjdghks95.project.rol.security.service.UserLoginRememberMeService;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final FormAuthenticationProvider formAuthenticationProvider;
    private final DataSource dataSource;
    private final FormUserDetailService formUserDetailService;
    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.authenticationProvider(formAuthenticationProvider);
        return auth.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http
//                .csrf().disable()
                .httpBasic().disable();

        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/signUp", "/logout", "/check/sendSMS").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("remember-me", "JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/")

                .and()
                .rememberMe()
                .tokenValiditySeconds(3600)
                .rememberMeServices(rememberMeServices(tokenRepository()))

                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .and()
                .ignoring()
                .mvcMatchers("/icon/**", "/js/**", "/css/**", "/font/**", "/img/**", "/resources/**", "/error")
                .antMatchers("/h2-console/**", "/favicon.ico");
    }

    /** PersistentTokenBasedRememberMeServices 를 위한 저장소 */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // DataSource 설정
        return jdbcTokenRepository;
    }

    /** Custom PersistentTokenBasedRememberMeServices */
    @Bean
    public RememberMeServices rememberMeServices(PersistentTokenRepository tokenRepository) {
        return new UserLoginRememberMeService("rememberMeKey", formUserDetailService, tokenRepository);
    }
}
