package com.clone.kukka.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    //JWT의 Payload에 해당하는 부분
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    // implements한 InitializingBean의 afterPropertiesSet()을 override
    // why?
    // @Component로 Bean이 생성되고, public JwtTokenProvider~ 로 의존성 주입 받은 후,
    // 주입 받은 String secret값을 Base64.decode()해서 key 변수(this.key)에 할당 해주기 위해서
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // role?
    // Authentication 객체의 권한 정보를 이용해서 Token을 생성하는 메소드
    // how?
    // 1. authentication 파라미터를 받아서, authorities(권한들)에 할당 (getAuthorities() = 사용자가 가진 모든 role 정보 get)
    //      ex) admin 계정으로 접속 시, getAuthorities하면 ROLE_USER, ROLE_ADMIN을 얻을 수 있고,
    //          stream()
    // 2. Date validity에 application.properties에서 생성한 Token 만료 시간을 설정
    // 3. return Jwts.builder()으로 생성한 JWT Token
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName()) // payload
                .claim(AUTHORITIES_KEY, authorities) // payload
                .signWith(key, SignatureAlgorithm.HS512) // signature
                .setExpiration(validity) // payload?
                .compact();
    }

    // role?
    // Token을 파라미터로 받아서 Token에 담긴 권한정보를 이용해, Authentication 객체를 return하는 메소드
    // how?
    // 1. 파라미터로 받은 token을 이용해 .parseClaimsJws(token)로 claims을 만든다.
    // 2. Collection으로 claims에서 권한 정보를 빼낸다.
    // 3. authorities(권한 정보들)을 이용해서 User 객체 principal을 만든다.
    // 4. principal, token, authorities을 이용해서 Authentication 객체인 UsernamePasswordAuthenticationToken을 return
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails user
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // role?
    // token의 유효성 검사
    // how?
    // 1. token을 파라미터로 받는다.
    // 2. parserBuilder()로 파싱한다.
    // 3. 파싱하면서 나오는 Exception들을 catch하고, 문제가 있으면 false
    // 4. 문제 없으면 return true
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // Comment create에 쓰일 username을 token에서 파싱
    public String getUserPk(String token) {
        String UserPk = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody().getSubject();
        return UserPk;
    }
}
