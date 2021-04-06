package com.clone.kukka.config;

import com.clone.kukka.jwt.JwtAccessDeniedHandler;
import com.clone.kukka.jwt.JwtAuthenticationEntryPoint;
import com.clone.kukka.jwt.JwtSecurityConfig;
import com.clone.kukka.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 기본적인 web 보안 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) // preAuthorize 어노테이션을 메소드 단위로 추가하기 위해 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 추가적인 security 설정, "implements WebSecurityConfigurer"와 동일
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 주입
    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // passwordEncoder로 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                );
    }

    @Override // extends한 WebSecurityConfigurerAdapter의 configure을 override 한다.
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Token을 사용하기 때문에 csrf 설정은 disable

                .exceptionHandling() // exceptionHandling에 직접 만든 jwtAuthenticationEntryPoint, jwtAccessDeniedHandler 클래스를 사용
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and() // h2-console을 위한 설정
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session을 사용하지 않기 때문에 session 설정을 STATELESS로 지정

                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                .antMatchers("/api/authenticate").permitAll() // Token을 받기 위한 로그인 api,
                .antMatchers("/api/signup").permitAll()       // 회원가입 api는 Token이 없는 상태에서 요청하기 때문에 접근 허용
                .antMatchers("/api/products").permitAll()
                .anyRequest().authenticated() // 위 설정 외에는 모두 인증이 필요

                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider)); // JwtSecurityConfig 클래스 적용
                                                                 // : JwtFilter를 addFilterBefore 이용해서 Security logic에 등록했던 클래스
    }
}
