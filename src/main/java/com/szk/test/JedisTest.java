package com.szk.test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void test() {
		
		Jedis jedis = new Jedis("192.168.223.130",6379);
		int i = 0;        // 记录次数
		try {
			
			long start = System.currentTimeMillis();
			while(true) {
				
				long end = System.currentTimeMillis();
				if(end - start >= 1000) {
					break;
				}
				i++;
				jedis.set("test"+i, i+"");
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			jedis.close();
		}
		System.out.println("redis每秒操作: "+i+"次");
		
	}
	
	public static void test2() {
		
		long start = System.currentTimeMillis();
		int i = 0;
		while(true) {
			long end = System.currentTimeMillis();
			i++;
			if(end - start >= 1000) {
				break;
			}
			i++;
		}
		System.out.println("i: "+i);
		
		
	}
	
}
