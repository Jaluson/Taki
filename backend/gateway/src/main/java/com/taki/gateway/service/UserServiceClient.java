package com.taki.gateway.service;

import com.taki.bo.UserAuthenticationDetailsBo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "taki", path = "/taki/user")
public interface UserServiceClient {

    @GetMapping("/getUserDetailsByUsername")
    Mono<UserAuthenticationDetailsBo> getUserDetailsByUsername(@RequestParam String username);

}
