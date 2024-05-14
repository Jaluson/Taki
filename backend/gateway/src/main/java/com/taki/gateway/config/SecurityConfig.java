package com.taki.gateway.config;

import com.taki.gateway.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final TakiServerSecurityContextRepository takiServerSecurityContextRepository;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final TakiServerLogoutHandler serverLogoutHandler;

    // @Value("${fontUrl}")
    // private String fontUrl;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
        security.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(e ->
                        e.pathMatchers(HttpMethod.OPTIONS).permitAll()
                                .pathMatchers("/taki/user/login").permitAll()
                                .anyExchange().authenticated())
                .securityContextRepository(takiServerSecurityContextRepository)
                .formLogin(formLoginSpec -> formLoginSpec.loginPage("/login")
                        .authenticationSuccessHandler(authenticationSuccessHandler)
                        .authenticationFailureHandler(authenticationFailureHandler))
                .logout(logoutSpec -> logoutSpec.logoutHandler(serverLogoutHandler));
        return security.build();
    }

    @Bean
    DelegatingReactiveAuthenticationManager reactiveAuthenticationManager() {
        List<ReactiveAuthenticationManager> managers = new ArrayList<>(1);
        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService));
        return new DelegatingReactiveAuthenticationManager(managers);
    }

}
