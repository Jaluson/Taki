package com.taki.business.user;

import com.taki.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taki.vo.currency.R;

/**
* @author Akiki
* @description 针对表【user】的数据库操作Service
* @createDate 2024-05-14 23:46:47
*/
public interface IUserService extends IService<User> {

    /**
     * 1.生成邮箱对应的 验证码 并缓存到Redis k:v---> email:code
     * 2.发送验证码到指定邮箱
     * @param email
     * @return 指定邮箱
     */
    R<Object> generatorVerifyCode(String email);

}
