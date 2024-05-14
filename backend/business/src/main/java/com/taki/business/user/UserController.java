package com.taki.business.user;

import com.taki.entity.User;
import com.taki.vo.LoginVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<List<User>> test(@RequestBody LoginVo loginVo){
        List<User> list = userService.list();
        return ResponseEntity.ok(list);
    }
}
