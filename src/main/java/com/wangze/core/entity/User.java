package com.wangze.core.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: 王泽20
 * 一个对象序列化的接口，一个类只有实现了Serializable接口，它的对象才能被序列化。
 *    从说明中我们可以看到，如果我们没有自己声明一个serialVersionUID变量,接口会默认生成一个serialVersionUID
 *    但是强烈建议用户自定义一个serialVersionUID,因为默认的serialVersionUID对于class的细节非常敏感，反序列化时可能会导致InvalidClassException这个异常。
 *    在前面我们已经新建了一个实体类User实现Serializable接口，并且定义了serialVersionUID变量。
 */
@Component
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer user_Id;     //用户id
    private String user_code;   //用户账号
    private String user_name;   //用户名称
    private String user_password;   //用户密码
    private Integer user_state;     //用户状态





}
