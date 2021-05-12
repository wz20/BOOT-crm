package com.wangze.core.controller;

import com.wangze.core.entity.User;
import com.wangze.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @author: 王泽20
 */
@Controller
public class UserController {
    @Autowired          //依赖注入
    private UserService userService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String usercode, String password, Model model, HttpSession session){
        User user = userService.findUser(usercode,password);
        if(user != null){
                session.setAttribute("USER_SESSION",user);
                return "redirect:customer/list";
        }
        model.addAttribute("msg","账号或密码错误，请重新输入");

        return "login";
    }
    //测试拦截器功能
    @RequestMapping("/tocon")
    public String tocon(){
        return "customer";
    }

    /* 退出登录 */
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();  //清除session
        return "redirect:login";

    }

    //向用户登录界面跳转
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }


}
