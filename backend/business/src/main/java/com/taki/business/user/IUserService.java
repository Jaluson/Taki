package com.taki.business.user;

import com.taki.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taki.vo.currency.R;
import com.taki.vo.user.RegisterVo;

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

    /**
     * 校验验证码是否正确
     *
     * @param email 邮箱
     * @param verificationCode 验证码
     * @return true.正确 false.错误
     */
    boolean verifyCode(String email, String verificationCode);

    /**
     * 注册
     * @param registerVo 注册对象
     * @return true.成功 false.失败
     */
    boolean register(RegisterVo registerVo);

}
