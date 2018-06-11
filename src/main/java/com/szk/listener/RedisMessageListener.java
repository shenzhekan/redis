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
		
		// �����Ϣ
		byte[] body = message.getBody();
		// ʹ��ֵ���л���ת����Ϣ
		String mesBody = (String) getRedisTemplate().getValueSerializer().deserialize(body);
		System.out.println("message: "+mesBody);
		// ��ȡ channel:ͨ��
		byte[] channel = message.getChannel();
		// ʹ���ַ������л���ת��
		String channelStr = (String) getRedisTemplate().getStringSerializer().deserialize(channel);
		System.out.println("channel: "+channelStr);
		// ��������ת��
		String bytesStr = new String(bytes);
		System.out.println("�������ƣ�"+bytesStr);
	}

	
}
