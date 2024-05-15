package com.taki.vo.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterVo {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String inviteCode;
}
