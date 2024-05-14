package com.taki.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceClient userServiceClient;


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userServiceClient.getUserDetailsByUsername(username).flatMap(userAuthenticationDetailsBo -> {
            List<String> identities = userAuthenticationDetailsBo.getIdentities();
            Set<SimpleGrantedAuthority> authorities = identities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
            UserDetails user = User.builder()
                    .username(userAuthenticationDetailsBo.getAccount())
                    .password(passwordEncoder.encode(userAuthenticationDetailsBo.getPwd()))
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false).build();
            return Mono.just(user);
        });
    }
}
