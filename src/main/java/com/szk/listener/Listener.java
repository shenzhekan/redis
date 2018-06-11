package com.szk.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Listener {

	public static void publicSubscribe() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		String channel = "chat";
		// convertAndSend ��������������chat������Ϣ�ģ�
		redisTemplate.convertAndSend(channel, "szk94686868");
		
	}
	
}
