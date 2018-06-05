package com.szk.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.szk.pojo.Role;


public class JedisTestClass {

	
	@Test
	public void test1() {
	
		JedisTest.test();
		
	}
	
	@Test
	public void test2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Role role = new Role(1L,"role_Name_1","note_1");
		RedisTemplate redisTemplate =  applicationContext.getBean(RedisTemplate.class);
		redisTemplate.opsForValue().set("role_1", role);
		Role role2 = (Role) redisTemplate.opsForValue().get("role_1");
		System.out.println(role2.getRoleName());
	}
	
}
