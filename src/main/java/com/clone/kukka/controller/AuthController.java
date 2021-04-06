package com.clone.kukka.controller;

import com.clone.kukka.dto.LoginDto;
import com.clone.kukka.dto.TokenDto;
import com.clone.kukka.jwt.JwtFiler;
import com.clone.kukka.jwt.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        // LoginDto의 username, password를 파라미터로 받아서,
        // Token 객체 생성 (UsernamePasswordAuthenticationToken authenticationToken)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken을 이용해 Authentication 객체를 생성하려고 authenticate 메소드가 실행됨
        // 실행될 때 CustomUserDetailsService의 loadUserByUsername 메소드가 실행됨
        // 그 결과를 이용해 authentication 객체를 생성
        // 이를 SecurityContext에 저장
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 저장한 인증정보를 기준으로 jwt 토큰을 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFiler.AUTHORIZATION_HEADER, "Bearer " + jwt); // response Header에 jwt 넣어주고,

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK); // TokenDto를 이용해 ResponseBody에도 jwt 넣어서 return
    }
}
