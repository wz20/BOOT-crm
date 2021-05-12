package com.wangze.core.dao;

import com.wangze.core.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: 王泽20
 * 通过账号密码查询用户
 */
@Repository
public interface UserDao {

    public User findUser(@Param("usercode") String usercode, @Param("password") String password);
    //当你使用了使用@Param注解来声明参数时，如果使用 #{} 或 ${} 的方式都可以。

}
