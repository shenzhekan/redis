package com.szk.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Hash {
	
	public static void HashTest1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		String key = "hash";
		Map<String, String> map = new HashMap<String, String>();
		map.put("f1", "val1");
		map.put("f2", "val2");
		// 相当于  hmset 命令
		redisTemplate.opsForHash().putAll(key,map);
		
		// 相当于  hset 命令
		redisTemplate.opsForHash().put(key, "f3", "6");
		printValueForHash(redisTemplate, key, "f3");
		
		// 相当于  hexists
		boolean exists = redisTemplate.opsForHash().hasKey(key, "f3");
		System.out.println("hash中是否存在键f3: "+exists);
		
		// 相当于  hgetall
		Map keyValMap = redisTemplate.opsForHash().entries(key);
		keyValMap.forEach((k,v) -> {
			System.out.println("key:"+k+"value:"+v);
		});
		
		// 相当于  hincrby
		redisTemplate.opsForHash().increment(key, "f3", 6);
		printValueForHash(redisTemplate, key, "f3");
		
		// 相当于  hincrbyfloat
		redisTemplate.opsForHash().increment(key, "f3", 0.66);
		printValueForHash(redisTemplate, key, "f3");
		
		// 相当于  hvals
		List valueList = redisTemplate.opsForHash().values(key);   //values() 返回的是List 集合
		System.out.print("hvals: ");
		valueList.forEach(x -> {
			System.out.print(x);
		});
		
		// 相当于  hkeys
		Set setKeys = redisTemplate.opsForHash().keys(key);       //keys() 返回的是set集合
		System.out.print("hkeys: ");
		setKeys.forEach(x -> {
			System.out.print(x+" ");
		});	
		System.out.println();
		
		// 相当于  hmget
		List valueList2 = redisTemplate.opsForHash().multiGet(key, setKeys);   //参数里加入的是set类型的键集合
		System.out.print("hmget: ");
		valueList2.forEach(x -> {
			System.out.print(x+" ");
		});
		System.out.println();
		
		// 相当于  hsetnx
		boolean hsetnxSuccess = redisTemplate.opsForHash().putIfAbsent(key, "f4", "val4");
		System.out.println("hsetnx: "+hsetnxSuccess);
		
		// 相当于  hdel 
		redisTemplate.opsForHash().delete(key, "f1","f2");
		
	}
	
	/**
	 * 获取指定hash结构中的field所对应的值
	 * @param redisTemplate
	 * @param key
	 * @param field
	 */
	public static void printValueForHash(RedisTemplate redisTemplate, String key, String field) {
		// 相当于  hget 命令
		String value = (String) redisTemplate.opsForHash().get(key, field);
		System.out.println("值为："+value);
	}
}
