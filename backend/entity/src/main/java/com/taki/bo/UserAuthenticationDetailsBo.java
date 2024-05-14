package com.taki.bo;


import lombok.Data;

import java.util.List;

@Data
public class UserAuthenticationDetailsBo {
    private String account;
    private String pwd;
    private List<String> identities;
}
