package com.szk.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.szk.pojo.Role;


public class JedisTestClass {

	/**
	 * 测试每秒redis的读写次数
	 */
	@Test
	public void test1() {
	
		JedisTest.test();
		
	}
	
	/**
	 * 测试序列化和反序列化的实现：对象类Role的存入和读取
	 */
	@Test
	public void test2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Role role = new Role(1L,"role_Name_1","note_1");
		RedisTemplate redisTemplate =  applicationContext.getBean(RedisTemplate.class);
		
		// 这里有一个问题，不能保证每次使用RedisTemplate是操作的同一个对redis的连接，
		redisTemplate.opsForValue().set("role_1", role);
		Role role2 = (Role) redisTemplate.opsForValue().get("role_1");
		System.out.println(role2.toString());
	}
	
	/**
	 *  使用   SessionCallback 接口实现将多个命令放在同一个redis连接中
	 *  
	 */
	@Test
	public void test3() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		Role role = new Role(2, "smy", "note_2");
		
		// 为了实现set 和  get 方法在同一个连接池的同一个redis连接中进行操作
		// SessionCallback 接口，可以把多个命令放在同一个redis连接中去执行
		SessionCallback<Role> callback = new SessionCallback<Role>() {

			@Override
			public Role execute(RedisOperations operations) throws DataAccessException {
				operations.boundValueOps("role_2").set(role);
				return (Role) operations.boundValueOps("role_2").get();
			}
		};
		Role saveRole = (Role) redisTemplate.execute(callback);
		System.out.println("role2: "+saveRole.toString());
		
	}
	
}
