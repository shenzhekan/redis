package com.szk.string;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.szk.pojo.Role;

public class StringTest1 {
	
	/**
	 *   String ：字符串操作
	 */
	public static void StringTest1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 实例化redisTemplate
		RedisTemplate redisTemplate =  applicationContext.getBean(RedisTemplate.class);
		// 设值
		redisTemplate.opsForValue().set("key1", "value1");
		redisTemplate.opsForValue().set("key2", "value2");
		// 通过  key 取值
		String value1 = (String) redisTemplate.opsForValue().get("key1");
		System.out.println("key2: "+value1);
		// 通过  key 删除值
		redisTemplate.delete("key2");
		// 求长度
		Long length = redisTemplate.opsForValue().size("key1");
		System.out.println("key1 的长度为："+length);
		// 设置新值并返回旧值
		String oldValue = (String) redisTemplate.opsForValue().getAndSet("key1", "new_value1");
		System.out.println("oldValue: "+oldValue);
		// 通过  key 取值
		String value2 = (String) redisTemplate.opsForValue().get("key1");
		System.out.println("newValue: "+value2);
		// 求子串
		String rangeValue = (String) redisTemplate.opsForValue().get("key1",0,10);
		System.out.println("子串： "+rangeValue);
		// 追加字符串到末尾，返回新串的长度
		int newLen = redisTemplate.opsForValue().append("key1", "_app");
		System.out.println("追加字符串到末尾，返回新串的长度: "+newLen);
		System.out.println("最后的key1为："+redisTemplate.opsForValue().get("key1"));
		
	}
	
	/**
	 *   Redis 运算
	 *   	关于减法，原值必须是整数,编译通过,但是运行之后会产生异常
	 */
	public static void StringTest2() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		redisTemplate.opsForValue().set("i", "8");
		printCurrValue(redisTemplate,"i");
		// 增 1 
		redisTemplate.opsForValue().increment("i", 1);
		printCurrValue(redisTemplate, "i");
		// 减 1
		redisTemplate.getConnectionFactory().getConnection()
			.decr(redisTemplate.getKeySerializer().serialize("i"));
		printCurrValue(redisTemplate, "i");
		// 减 6
		redisTemplate.getConnectionFactory().getConnection()
			.decrBy(redisTemplate.getKeySerializer().serialize("i"),6);
		printCurrValue(redisTemplate, "i");
		// 浮点计算：加2.3
		redisTemplate.opsForValue().increment("i",2.3);
		printCurrValue(redisTemplate, "i");
		
		
	}

	/**
	 *   输出  key 对应的值
	 * @param redisTemplate
	 * @param key
	 */
	public static void printCurrValue(RedisTemplate redisTemplate, String key) {
		System.out.println(redisTemplate.opsForValue().get(key));
	}
	
	
	
}
