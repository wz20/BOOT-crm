package com.wangze.core.service;

import com.wangze.core.dao.UserDao;
import com.wangze.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 王泽20
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
//注入成员变量userDao

    @Override
    public User findUser(String usercode, String password) {
        User user =this.userDao.findUser(usercode,password);
        return user;
    }
}
