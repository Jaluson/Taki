package com.taki.utils;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String projectName;
    private final SimpleMailMessage message;

    public MailUtil(SimpleMailMessage message) {
        this.message = message;
    }

    /**
     * 构造一个简单消息
     *
     * @param to      发送到的地址
     * @param subject 标题
     * @param content 内容
     * @return 简单邮箱消息
     */
    public SimpleMailMessage normalMsg(String to, String subject, String content) {
        // 参数校验
        if (StringUtils.isBlank(to)) {
            throw new IllegalArgumentException("发送地址不能为空");
        }
        if (StringUtils.isBlank(subject)) {
            throw new IllegalArgumentException("邮件主题不能为空");
        }

        // 调用默认消息
        SimpleMailMessage msg = new SimpleMailMessage();
        // 默认消息属性传递
        message.copyTo(msg);

        // 设置消息
        msg.setTo(to);
        msg.setSubject(StringUtils.isBlank(subject) ? projectName : subject);
        msg.setText(content);
        return msg;
    }

}
