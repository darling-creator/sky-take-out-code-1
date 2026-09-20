package com.sky.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sky.Interceptor.JwtTokenUserInterceptor;

public class WebMvcConfiguration implements WebMvcConfigurer {
	@Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;
	
	//注册用户端拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/login")
                .excludePathPatterns("/user/shop/status");
    }
}
