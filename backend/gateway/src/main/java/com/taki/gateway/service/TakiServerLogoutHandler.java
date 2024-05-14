package com.taki.gateway.service;

import com.taki.gateway.config.JWTConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TakiServerLogoutHandler implements ServerLogoutHandler {

    private final JWTConfig jwtConfig;

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        exchange.getExchange().getRequest().getCookies().remove(jwtConfig.getHeader());
        return Mono.empty();
    }
}
