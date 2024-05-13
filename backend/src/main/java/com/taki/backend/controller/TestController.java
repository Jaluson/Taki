package com.taki.backend.controller;

import com.taki.backend.vo.LoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/login")
    public Object login(@RequestBody LoginVO loginVO) {
        return loginVO;
    }

}
