package com.szk.hyper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基数：给一个有重复元素的数据集合（一般是很大的数据集合）评估需要的空间单元数
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
		System.out.println("h1的基数值为："+size);
		redisTemplate.opsForHyperLogLog().union("h3", "h1", "h2");
		size = redisTemplate.opsForHyperLogLog().size("h3");
		System.out.println("合并h1,h2,获取h3的基数值；"+size);
		
	}
	
}
