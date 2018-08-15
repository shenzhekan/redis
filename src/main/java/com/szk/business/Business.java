package com.szk.business;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * Redis 事务机制
 * @author admin
 *
 */
public class Business {

	/**
	 * Redis 的基础事务
	 */
	public static void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		SessionCallback callback = (SessionCallback) (RedisOperations ops) -> {
			ops.multi();                                                 // 开启事务
			ops.boundValueOps("key1").set("value1");
			
			// 由于这里只是将命令放入队列，所以value值为null
			String value = (String) ops.boundValueOps("key1").get();
			System.out.println("由于这里只是将命令放入队列，所以value值为null:value: "+value);
			
			// 将之前保存到队列中的命令的结果保存到list中
			List list = ops.exec();
			value = (String) redisTemplate.opsForValue().get("key1");
			return value;
		};
		
		// 执行Redis命令
		String value = (String) redisTemplate.execute(callback);
		System.out.println(value);
		
	}
	
	/**
	 * 使用Spring使用 Redis 的流水线技术
	 * 经测试   100000 条数据的读/写操作， 用时850毫秒，一比较，mysql的处理能力就是垃圾    ╮(╯▽╰)╭
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
		System.out.println("耗时："+(end - start)+" 毫秒");
		
	}
	
	
}
