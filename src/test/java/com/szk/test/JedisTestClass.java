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
	 * ����ÿ��redis�Ķ�д����
	 */
	@Test
	public void test1() {
	
		JedisTest.test();
		
	}
	
	/**
	 * �������л��ͷ����л���ʵ�֣�������Role�Ĵ���Ͷ�ȡ
	 */
	@Test
	public void test2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Role role = new Role(1L,"role_Name_1","note_1");
		RedisTemplate redisTemplate =  applicationContext.getBean(RedisTemplate.class);
		
		// ������һ�����⣬���ܱ�֤ÿ��ʹ��RedisTemplate�ǲ�����ͬһ����redis�����ӣ�
		redisTemplate.opsForValue().set("role_1", role);
		Role role2 = (Role) redisTemplate.opsForValue().get("role_1");
		System.out.println(role2.toString());
	}
	
	/**
	 *  ʹ��   SessionCallback �ӿ�ʵ�ֽ�����������ͬһ��redis������
	 *  
	 */
	@Test
	public void test3() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		Role role = new Role(2, "smy", "note_2");
		
		// Ϊ��ʵ��set ��  get ������ͬһ�����ӳص�ͬһ��redis�����н��в���
		// SessionCallback �ӿڣ����԰Ѷ���������ͬһ��redis������ȥִ��
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
