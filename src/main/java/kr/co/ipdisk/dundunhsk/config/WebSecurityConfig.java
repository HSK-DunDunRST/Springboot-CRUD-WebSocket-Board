package kr.co.ipdisk.dundunhsk.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // Spring의 설정 파일임을 나타내는 어노테이션
@EnableWebSecurity // Spring Security를 활성화하는 어노테이션
@EnableMethodSecurity(prePostEnabled = true) // 메서드 수준의 보안 처리를 활성화함 (ex: @PreAuthorize)
public class WebSecurityConfig {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // HttpSecurity 객체를 사용하여 웹 보안 설정을 구성
        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // 모든 요청에 대해 인증 없이 접근을 허용함
                        .requestMatchers(new AntPathRequestMatcher("/**"))
                        .permitAll())
                // CSRF 보호를 비활성화함
                .csrf((csrf) -> csrf.disable())
                // X-Frame-Options 헤더를 SAMEORIGIN으로 설정하여 동일 출처에서만 iframe으로 로드 가능하게 함
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 사용자 로그인 설정
                .formLogin((formLogin) -> formLogin.loginPage("/login")
                        .usernameParameter("userId") // 로그인 폼에서 사용자가 입력한 아이디의 파라미터 이름
                        .passwordParameter("userPwd")
                        .defaultSuccessUrl("/")
                        .permitAll())// 로그인 페이지 경로 설정
                // 사용자 로그아웃 설정
                .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 경로 설정
                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 이동할 페이지 설정
                        .invalidateHttpSession(true)); // 로그아웃 시 세션 무효화
        return httpSecurity.build(); // 위의 설정을 바탕으로 SecurityFilterChain 객체를 생성하여 반환
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 비밀번호를 암호화하기 위한 PasswordEncoder를 Bean으로 등록
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder를 사용하여 비밀번호를 암호화
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        // AuthenticationManager를 Bean으로 등록
        return authenticationConfiguration.getAuthenticationManager();
    }
}
