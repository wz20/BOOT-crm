package com.wangze.core.interceptor;

import com.wangze.core.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: 王泽20
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws ServletException, IOException {
        //获取请求的url
        String url =request.getRequestURI();
        //拦截不是登录的请求
        if(url.contains("/login")){
            return true;
        }
        //获取session
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("USER_SESSION");
        //判断session中是否有用户数据，如果有，则返回true，继续向下执行
        if (user != null){
            return true;
        }
        //不符合条件的给出提示信息，并转发到登录界面
        request.setAttribute("msg","您还没有登录，请先登录");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);  //抛出异常
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
