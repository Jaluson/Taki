package com.taki.business.user;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taki.entity.User;
import com.taki.mapper.UserMapper;
import com.taki.utils.MailUtil;
import com.taki.utils.Utils;
import com.taki.vo.currency.R;
import com.taki.vo.user.RegisterVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Akiki
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-05-14 23:46:47
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements IUserService {

    private final int CODE_LENGTH = 6;
    private final int CODE_TIMEOUT = 10;
    private final TimeUnit CODE_TIME_UNIT = TimeUnit.MINUTES;
    private final String SUBJECT = "Taki验证码";
    private final String MODEL_STR = """
                        您的验证码为 %s,有效期%d分钟,如非本人操作，请忽略本邮件.
                        为了确保您的帐号安全,请不要将此邮件转发给任何人.
            """;

    private final RedisTemplate<String, Object> redisTemplate;
    private final MailUtil mailUtil;
    private final JavaMailSender mailSender;


    @Override
    public R<Object> generatorVerifyCode(String email) {
        /// 1.生成验证码并缓存到redis
        String code = Utils.randomCode(CODE_LENGTH);
        redisTemplate.opsForValue().set(email, code, CODE_TIMEOUT, CODE_TIME_UNIT);
        /// 2.发送验证码到指定邮箱
        SimpleMailMessage simpleMailMessage = mailUtil.normalMsg(email, SUBJECT, MODEL_STR.formatted(code, CODE_TIMEOUT));
        // 发送
        mailSender.send(simpleMailMessage);
        return R.success();
    }

    @Override
    public boolean verifyCode(String email, String verificationCode) {
        /// 1.校验验证码
        String code = (String) redisTemplate.opsForValue().get(email);
        return code != null && code.equals(verificationCode);
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        Date now = new Date();
        User user = User.builder()
                .account(registerVo.getUsername())
                .pwd(registerVo.getPassword())
                .ban(false)
                .alias(UUID.randomUUID().toString())
                .gender(true)
                .address("")
                .lastLoginTime(now)
                .unsubcribe(false)
                .registrationDate(now).build();
        int insert = this.baseMapper.insert(user);
        return insert > 0;
    }
}




