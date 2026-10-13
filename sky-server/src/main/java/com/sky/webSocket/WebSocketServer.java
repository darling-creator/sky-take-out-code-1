package com.sky.webSocket;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

//该类主要负责发送消息
@Component
//设置前端连接地址ws://localhost:8080/ws
@ServerEndpoint("/ws")
public class WebSocketServer {
	//存放所有商家客户端连接
    private static Map<String, Session> sessionMap = new HashMap<>();
    
    //建立连接,商家页面打开，成功连上后端时自动执行
    @OnOpen
    public void onOpen(Session session) {
    	sessionMap.put(session.getId(),session);
    }
    
    //连接关闭,商家关闭页面/退出页面，自动执行
    @OnClose
    public void onClose(Session session){
        sessionMap.remove(session.getId());
    }
    
    //发消息给所有在线商家
    public void sentToAllOnline(String message) {
    	for( Session session : sessionMap.values() ) {
    		try {
                //发送文本消息给这个商家浏览器
    			//session当前这条通信管道
    			//getBasicRemote()拿到管道对面（浏览器）
    			//sendText()通过这条管道，发送一段文字过去
                session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
    	}
    }
}
