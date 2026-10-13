package com.sky.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
	//查询的代码
	//ServerEndpointExporter开启项目对@ServerEndpoint注解的支持。
	//开启WebSocket功能,来实现后续后端主动向前端发送消息
	@Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
