package com.taki.bussiness.user;

import com.taki.vo.LoginVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/test")
    public ResponseEntity<LoginVo> test(@RequestBody LoginVo loginVo){
        return ResponseEntity.ok(loginVo);
    }
}
