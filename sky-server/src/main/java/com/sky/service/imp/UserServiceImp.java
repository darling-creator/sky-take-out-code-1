package com.sky.service.imp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;

@Service
public class UserServiceImp implements UserService {
	//查询：
	//微信登录凭证校验接口地址
    public static final String WX_JSCODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    
	@Autowired
    private UserMapper userMapper;
	@Autowired
    private WeChatProperties weChatProperties;
	
	@Override
	public User wxLogin(UserLoginDTO userLoginDTO) {
		String code = userLoginDTO.getCode();
		//拿到openid
        String openid = getOpenId(code);

        //判断openid，如果为空，登录失败抛出异常
        if(openid == null || openid.equals("")){
            throw new RuntimeException("微信登录失败，获取openid为空");
        }

        //根据openid查询数据库用户
        User user = userMapper.getByOpenid(openid);

        //如果用户不存在 → 自动注册新用户
        if(user == null){
        	user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            userMapper.insert(user);
        }
        //老用户直接返回
        return user;
	}
	
	//查询的代码
	//发送httpGET请求访问微信接口，获取openid
	@SuppressWarnings("unused")
	private String getOpenId(String code){
        Map<String,String> param = new HashMap<>();
        param.put("appid",weChatProperties.getAppid());
        param.put("secret",weChatProperties.getSecret());
        param.put("js_code",code);
        param.put("grant_type","authorization_code");

        //拼接url参数
        StringBuilder urlSb = new StringBuilder(WX_JSCODE2SESSION_URL);
        urlSb.append("?");
        for(Map.Entry<String,String> entry : param.entrySet()){
            urlSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        //删掉最后多余的&符号
        String url = urlSb.substring(0,urlSb.length()-1);

        String openid = null;
        //创建http客户端，try‑with‑resource自动关闭流
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //拿到微信返回的json字符串
            String jsonResult = EntityUtils.toString(response.getEntity());
            Map<?, ?> map = JSON.parseObject(jsonResult,Map.class);
            openid = (String) map.get("openid");
        }catch (Exception e){
        }
        return openid;
    }
}
