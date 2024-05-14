package com.taki.gateway.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import com.taki.gateway.config.JWTConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TakiServerSecurityContextRepository implements ServerSecurityContextRepository {
    private final JWTConfig jwtConfig;
    private final JWTSigner jwtSigner;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie cookie = cookies.getFirst(jwtConfig.getHeader());

        if (Objects.isNull(cookie)){
            return Mono.empty();
        }
        String token = cookie.getValue();

        if (token.isBlank() || !JWTUtil.verify(token, jwtSigner)){
            return Mono.empty();
        }

        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();

        JSONArray authorities = payloads.getJSONArray("authorities");
        Set<SimpleGrantedAuthority> authoritiesSet = authorities.stream().map(m -> new SimpleGrantedAuthority(m.toString())).collect(Collectors.toSet());

        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(payloads.getStr("username"),
                payloads.getStr("password"),
                authoritiesSet));
        return Mono.just(securityContext);
    }
}
