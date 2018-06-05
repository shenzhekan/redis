package com.szk.string;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.szk.pojo.Role;

public class StringTest1 {
	
	/**
	 *   String ���ַ�������
	 */
	public static void StringTest1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		// ʵ����redisTemplate
		RedisTemplate redisTemplate =  applicationContext.getBean(RedisTemplate.class);
		// ��ֵ
		redisTemplate.opsForValue().set("key1", "value1");
		redisTemplate.opsForValue().set("key2", "value2");
		// ͨ��  key ȡֵ
		String value1 = (String) redisTemplate.opsForValue().get("key1");
		System.out.println("key2: "+value1);
		// ͨ��  key ɾ��ֵ
		redisTemplate.delete("key2");
		// �󳤶�
		Long length = redisTemplate.opsForValue().size("key1");
		System.out.println("key1 �ĳ���Ϊ��"+length);
		// ������ֵ�����ؾ�ֵ
		String oldValue = (String) redisTemplate.opsForValue().getAndSet("key1", "new_value1");
		System.out.println("oldValue: "+oldValue);
		// ͨ��  key ȡֵ
		String value2 = (String) redisTemplate.opsForValue().get("key1");
		System.out.println("newValue: "+value2);
		// ���Ӵ�
		String rangeValue = (String) redisTemplate.opsForValue().get("key1",0,10);
		System.out.println("�Ӵ��� "+rangeValue);
		// ׷���ַ�����ĩβ�������´��ĳ���
		int newLen = redisTemplate.opsForValue().append("key1", "_app");
		System.out.println("׷���ַ�����ĩβ�������´��ĳ���: "+newLen);
		System.out.println("����key1Ϊ��"+redisTemplate.opsForValue().get("key1"));
		
	}
	
	/**
	 *   Redis ����
	 *   	���ڼ�����ԭֵ����������,����ͨ��,��������֮�������쳣
	 */
	public static void StringTest2() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		redisTemplate.opsForValue().set("i", "8");
		printCurrValue(redisTemplate,"i");
		// �� 1 
		redisTemplate.opsForValue().increment("i", 1);
		printCurrValue(redisTemplate, "i");
		// �� 1
		redisTemplate.getConnectionFactory().getConnection()
			.decr(redisTemplate.getKeySerializer().serialize("i"));
		printCurrValue(redisTemplate, "i");
		// �� 6
		redisTemplate.getConnectionFactory().getConnection()
			.decrBy(redisTemplate.getKeySerializer().serialize("i"),6);
		printCurrValue(redisTemplate, "i");
		// ������㣺��2.3
		redisTemplate.opsForValue().increment("i",2.3);
		printCurrValue(redisTemplate, "i");
		
		
	}

	/**
	 *   ���  key ��Ӧ��ֵ
	 * @param redisTemplate
	 * @param key
	 */
	public static void printCurrValue(RedisTemplate redisTemplate, String key) {
		System.out.println(redisTemplate.opsForValue().get(key));
	}
	
	
	
}
