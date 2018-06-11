package com.szk.hyper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * ��������һ�����ظ�Ԫ�ص����ݼ��ϣ�һ���Ǻܴ�����ݼ��ϣ�������Ҫ�Ŀռ䵥Ԫ��
 * @author admin
 *
 */
public class HyperLogLog {

	public static void test1() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		redisTemplate.opsForHyperLogLog().add("h1", "a", "b", "c", "d", "a");
		redisTemplate.opsForHyperLogLog().add("h2", "a", "z");
		Long size = redisTemplate.opsForHyperLogLog().size("h1");
		System.out.println("h1�Ļ���ֵΪ��"+size);
		redisTemplate.opsForHyperLogLog().union("h3", "h1", "h2");
		size = redisTemplate.opsForHyperLogLog().size("h3");
		System.out.println("�ϲ�h1,h2,��ȡh3�Ļ���ֵ��"+size);
		
	}
	
}
