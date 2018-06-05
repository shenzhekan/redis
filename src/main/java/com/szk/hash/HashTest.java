package com.szk.hash;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class HashTest {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
	
	public static void HashTest1() {
		
		String key = "hash";
		Map<String, String> map = new HashMap<String, String>();
		map.put("f1", "val1");
		map.put("f2", "val2");
		// �൱��  hmset ����
		redisTemplate.opsForHash().putAll(key,map);
		// �൱��  hset ����
		redisTemplate.opsForHash().put(key, "f3", "val3");
		
		
	}
	
	/**
	 * ��ȡָ��hash�ṹ�е�field����Ӧ��ֵ
	 * @param redisTemplate
	 * @param key
	 * @param field
	 */
	public static void printValueForHash(RedisTemplate redisTemplate, String key, String field) {
		// �൱��  hget ����
		String value = (String) redisTemplate.opsForHash().get(key, key);
		System.out.println("ֵΪ��"+value);
	}
}
