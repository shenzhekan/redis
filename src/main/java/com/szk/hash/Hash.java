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
		// �൱��  hmset ����
		redisTemplate.opsForHash().putAll(key,map);
		
		// �൱��  hset ����
		redisTemplate.opsForHash().put(key, "f3", "6");
		printValueForHash(redisTemplate, key, "f3");
		
		// �൱��  hexists
		boolean exists = redisTemplate.opsForHash().hasKey(key, "f3");
		System.out.println("hash���Ƿ���ڼ�f3: "+exists);
		
		// �൱��  hgetall
		Map keyValMap = redisTemplate.opsForHash().entries(key);
		keyValMap.forEach((k,v) -> {
			System.out.println("key:"+k+"value:"+v);
		});
		
		// �൱��  hincrby
		redisTemplate.opsForHash().increment(key, "f3", 6);
		printValueForHash(redisTemplate, key, "f3");
		
		// �൱��  hincrbyfloat
		redisTemplate.opsForHash().increment(key, "f3", 0.66);
		printValueForHash(redisTemplate, key, "f3");
		
		// �൱��  hvals
		List valueList = redisTemplate.opsForHash().values(key);   //values() ���ص���List ����
		System.out.print("hvals: ");
		valueList.forEach(x -> {
			System.out.print(x);
		});
		
		// �൱��  hkeys
		Set setKeys = redisTemplate.opsForHash().keys(key);       //keys() ���ص���set����
		System.out.print("hkeys: ");
		setKeys.forEach(x -> {
			System.out.print(x+" ");
		});	
		System.out.println();
		
		// �൱��  hmget
		List valueList2 = redisTemplate.opsForHash().multiGet(key, setKeys);   //������������set���͵ļ�����
		System.out.print("hmget: ");
		valueList2.forEach(x -> {
			System.out.print(x+" ");
		});
		System.out.println();
		
		// �൱��  hsetnx
		boolean hsetnxSuccess = redisTemplate.opsForHash().putIfAbsent(key, "f4", "val4");
		System.out.println("hsetnx: "+hsetnxSuccess);
		
		// �൱��  hdel 
		redisTemplate.opsForHash().delete(key, "f1","f2");
		
	}
	
	/**
	 * ��ȡָ��hash�ṹ�е�field����Ӧ��ֵ
	 * @param redisTemplate
	 * @param key
	 * @param field
	 */
	public static void printValueForHash(RedisTemplate redisTemplate, String key, String field) {
		// �൱��  hget ����
		String value = (String) redisTemplate.opsForHash().get(key, field);
		System.out.println("ֵΪ��"+value);
	}
}
