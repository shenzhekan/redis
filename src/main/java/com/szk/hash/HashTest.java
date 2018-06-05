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
		// 相当于  hmset 命令
		redisTemplate.opsForHash().putAll(key,map);
		// 相当于  hset 命令
		redisTemplate.opsForHash().put(key, "f3", "val3");
		
		
	}
	
	/**
	 * 获取指定hash结构中的field所对应的值
	 * @param redisTemplate
	 * @param key
	 * @param field
	 */
	public static void printValueForHash(RedisTemplate redisTemplate, String key, String field) {
		// 相当于  hget 命令
		String value = (String) redisTemplate.opsForHash().get(key, key);
		System.out.println("值为："+value);
	}
}
