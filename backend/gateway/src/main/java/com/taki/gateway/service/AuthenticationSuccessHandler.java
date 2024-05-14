package com.taki.gateway.service;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSigner;
import com.taki.gateway.config.JWTConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JWTSigner jwtSigner;
    private final JWTConfig jwtConfig;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("验证成功");
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        if (webFilterExchange.getExchange().getRequest().getCookies().containsKey(jwtConfig.getHeader())) {
            return Mono.empty();
        }
        return sign(response, authentication); // 签发
    }

    private Mono<Void> sign(ServerHttpResponse response, Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        JWT jwt = JWT.create().setSigner(jwtSigner)
                .setPayload("username", principal.getUsername())
                .setPayload("password", principal.getPassword())
                .setPayload("authorities", authorities)
                .setIssuer(jwtConfig.getIssuer())
                .setExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()));

        response.addCookie(ResponseCookie.from(jwtConfig.getHeader(),
                        jwt.sign())
                .path("/").maxAge(jwtConfig.getExpirationTime() / 1000)
                .httpOnly(true)
                .build());
        DataBuffer authenticationSuccessResponse = response.bufferFactory().wrap("authentication success".getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(authenticationSuccessResponse));
    }
}
