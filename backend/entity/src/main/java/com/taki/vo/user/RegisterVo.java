package com.taki.vo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterVo {
    @NotNull(message = "用户名不能为空")
    @Size(min = 7, max = 21)
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 64, max = 64)
    private String password;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotNull(message = "验证码不能为空")
    @Size(min = 6, max = 6)
    private String verificationCode;

    private String inviteCode;
}
