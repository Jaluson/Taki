package com.taki.business.user;

import com.taki.entity.User;
import com.taki.exceptions.ErrorVerifyException;
import com.taki.vo.currency.R;
import com.taki.vo.user.LoginVo;
import com.taki.vo.user.RegisterVo;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    private final RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/login")
    public ResponseEntity<List<User>> test(@RequestBody LoginVo loginVo) {
        List<User> list = userService.list();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterVo registerVo) {
        // todo 邀请码机制后续补上 先做验证码
        if (!userService.verifyCode(registerVo.getEmail(), registerVo.getVerificationCode())) {
            throw new ErrorVerifyException("验证码未通过!");
        }
        boolean flag = userService.register(registerVo);
        
        return null;
    }

    @PostMapping("/generatorVerifyCode")
    public R<Object> generatorVerifyCode(@RequestParam @Validated @Email(message = "邮箱格式错误") String email) {
        return userService.generatorVerifyCode(email);
    }


}
