package com.clone.kukka.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFiler extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFiler.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private com.clone.kukka.jwt.JwtTokenProvider jwtTokenProvider;

    public JwtFiler(com.clone.kukka.jwt.JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // how?
    // 1. resolveToken 이용, httpServletRequest에서 token을 받아서 jwt에 할당
    // 2. vaildateToken으로 jwt(Token)의 유효성 검사 ㄱㄱ
    // 3. true: jwt의 authentication을 받아와서, SecurityContext에 set
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI(); // client에서 요청한 uri (ex) api/products)

        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication); // set Security Context 부분
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

// role? JwtFilter
// JWT를 위한 customer filter (GenericFilterBean을 extends 후, doFilter를 override하여 활용)
// how?
// 1. JwtFilter에 jwtTokenProvider를 주입
// override한 doFilter에 filtering 로직 작성
// role? doFilter
// JwtToken의 인증정보를 현재 실행 중인 SecurityContext에 저장
// role? resolveToken
// Request Header에서 Token 정보를 꺼내오기 위한 메소드
