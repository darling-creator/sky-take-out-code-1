package com.sky.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;

import io.jsonwebtoken.Claims;
@Component
public class JwtTokenUserInterceptor implements HandlerInterceptor {
	@Autowired
    private JwtProperties jwtProperties;
	
	//查询的逻辑
	//controller执行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头拿到token
        String token = request.getHeader("token");
        //判断token是否为空
        if(token == null || token.equals("")){
            //设置响应，返回未登录
            response.setStatus(401);
            return false;
        }
        //解析token，拿到claims里面userId
        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
        Long userId = Long.valueOf(claims.get("userId").toString());
        //存入ThreadLocal，后续service/controller直接拿当前登录用户id
        BaseContext.setCurrentId(userId);
        //放行
        return true;
    }
    
    //请求全部完成之后，清理ThreadLocal，防止内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}
