package com.szk.set;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class SetClass {

	/**
	 * redis 中的集合 Set 基本操作示例
	 */
	public static void test1() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		Set set = null;
		
		// 将元素存入集合
		redisTemplate.boundSetOps("set1").add("v1", "v2", "v3", "v4", "v5", "v6");
		redisTemplate.boundSetOps("set2").add("v2", "v4", "v6", "v8");
		
		// 求集合长度
		Long scard = redisTemplate.opsForSet().size("set1");
		System.out.println("set1的长度为："+scard);
		
		// 求差集
		set = redisTemplate.opsForSet().difference("set1", "set2");
		print(set);
		
		// 求交集
		set = redisTemplate.opsForSet().intersect("set1", "set2");
		print(set);
		
		// 判断是否是集合中的元素
		boolean isMember = redisTemplate.opsForSet().isMember("set1", "v1");
		System.out.println("v1 是否是集合 set1 中的元素："+isMember);
		
		// 获取集合中所有元素
		set = redisTemplate.opsForSet().members("set1");
		print(set);
		
		// 从集合中随机弹出一个元素
		String temp = (String) redisTemplate.opsForSet().pop("set1");
		System.out.println("从集合  set1 中随机弹出一个元素："+temp);
		
		// 随机获取一个集合的元素
		temp = (String) redisTemplate.opsForSet().randomMember("set1");
		System.out.println("从集合  set1 中随机获得一个元素："+temp);
		
		// 随机获得2个集合元素
		List list = redisTemplate.opsForSet().randomMembers("set1", 2);
		System.out.print("从集合  set1 中获得两个元素：");
		list.forEach(System.out::print);
		System.out.println();
		
		// 删除一个集合的元素，参数可以是多个
		redisTemplate.opsForSet().remove("set1", "v3");
		
		// 求两个集合的并集
		set = redisTemplate.opsForSet().union("set1", "set2");
		print(set);
		
		// 为之后的三个集合做比较
		printSet(redisTemplate, "set1");
		printSet(redisTemplate, "set2");
		
		// 求两个集合的差集，并保存到diff_set中
		redisTemplate.opsForSet().differenceAndStore("set1", "set2", "diff_set");
		printSet(redisTemplate, "diff_set");
		
		// 求两个集合的交集，并保存到inter_set中
		redisTemplate.opsForSet().intersectAndStore("set1", "set2", "inter_set");
		printSet(redisTemplate, "inter_set");
		
		// 求两个集合的并集，并保存到union_set中
		redisTemplate.opsForSet().unionAndStore("set1", "set2", "union_set");
		printSet(redisTemplate, "union_set");
		
	}
	
	/**
	 * 返回指定集合  Set 中的所有元素
	 * @param set
	 */
	public static void print(Set set) {
		System.out.print("集合中的值： ");
		set.forEach(x -> {
			System.out.print(x+" ");
		});
		System.out.println();
	}
	
	/**
	 * 返回Redis中指定集合 Set 中的所有元素
	 * @param redisTemplate
	 * @param set
	 */
	public static void printSet(RedisTemplate redisTemplate, String set) {
		Set set2 = new HashSet<>();
		set2 = redisTemplate.opsForSet().members(set);
		System.out.print("集合  "+set+"  中的元素为：");
		set2.forEach(x -> {
			System.out.print(x+" ");
		});
		System.out.println();
	}
	
}
