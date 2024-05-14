package com.taki.gateway.config;


import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Data
public class JWTConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTime}")
    private Long expirationTime;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.privateKey}")
    private String privateKey;

    @Bean
    JWTSigner jwtSigner() {
        return JWTSignerUtil.hs512(privateKey.getBytes(StandardCharsets.UTF_8));
    }
}
