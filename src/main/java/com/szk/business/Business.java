package com.szk.business;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * Redis �������
 * @author admin
 *
 */
public class Business {

	/**
	 * Redis �Ļ�������
	 */
	public static void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		SessionCallback callback = (SessionCallback) (RedisOperations ops) -> {
			ops.multi();                                                 // ��������
			ops.boundValueOps("key1").set("value1");
			
			// ��������ֻ�ǽ����������У�����valueֵΪnull
			String value = (String) ops.boundValueOps("key1").get();
			System.out.println("��������ֻ�ǽ����������У�����valueֵΪnull:value: "+value);
			
			// ��֮ǰ���浽�����е�����Ľ�����浽list��
			List list = ops.exec();
			value = (String) redisTemplate.opsForValue().get("key1");
			return value;
		};
		
		// ִ��Redis����
		String value = (String) redisTemplate.execute(callback);
		System.out.println(value);
		
	}
	
	/**
	 * ʹ��Springʹ�� Redis ����ˮ�߼���
	 * ������   100000 �����ݵĶ�/д������ ��ʱ850���룬һ�Ƚϣ�mysql�Ĵ���������������    �r(�s���t)�q
	 */
	public static void test2() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		SessionCallback callback = (SessionCallback) (RedisOperations ops) -> {
			for(int i = 0; i < 100000; i++) {
				int j = i+1;
				ops.boundValueOps("pipeline_key_"+j).set("pipeline_value_"+j);
				ops.boundValueOps("pipeline_key_"+j).get();
			}
			return null;
		};
		
		long start = System.currentTimeMillis();
		List result = redisTemplate.executePipelined(callback);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ��"+(end - start)+" ����");
		
	}
	
	
}
