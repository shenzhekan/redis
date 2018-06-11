package com.szk.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisMessageListener implements MessageListener{

	private RedisTemplate RedisTemplate;
	public RedisTemplate getRedisTemplate() {
		return RedisTemplate;
	}
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] bytes) {
		
		// 获得消息
		byte[] body = message.getBody();
		// 使用值序列化器转化消息
		String mesBody = (String) getRedisTemplate().getValueSerializer().deserialize(body);
		System.out.println("message: "+mesBody);
		// 获取 channel:通道
		byte[] channel = message.getChannel();
		// 使用字符串序列化器转化
		String channelStr = (String) getRedisTemplate().getStringSerializer().deserialize(channel);
		System.out.println("channel: "+channelStr);
		// 渠道名称转化
		String bytesStr = new String(bytes);
		System.out.println("渠道名称："+bytesStr);
	}

	
}
