package com.taki.business.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taki.entity.User;
import com.taki.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author Akiki
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-05-14 23:46:47
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements IUserService {

}




