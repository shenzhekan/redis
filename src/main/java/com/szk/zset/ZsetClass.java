package com.szk.zset;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public class ZsetClass {
	
	public static void test1() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		Set<TypedTuple> set1 = new HashSet<TypedTuple>();
		Set<TypedTuple> set2 = new HashSet<TypedTuple>();
		
		for (int i = 1; i <= 9; i++) {
			Double score1 = Double.valueOf(i);
			String value1 = "x"+i;
			String value2 = i % 2 == 1 ? "y" + i : "x" + i;
			TypedTuple tuple1 = new DefaultTypedTuple(value1, score1);
			TypedTuple tuple2 = new DefaultTypedTuple(value2, score1);
			set1.add(tuple1);
			set2.add(tuple2);
		}
		// 将元素插入有序集合
		redisTemplate.opsForZSet().add("zset1", set1);
		redisTemplate.opsForZSet().add("zset2", set2);
		
		// 统计在set1中的元素总数
		
		
		// 记分数为score,那么下面的方法就是求  3<=score<=6
		
		
	    // 从下标1开始截取5个元素,但是不返回分数,每一个元素都是String
		
		
		// 截取集合所有元素,并且对集合按分数排序,并返回分数,每一个元素是 TypedTuple
		
		
		// 将zset1 和zset2 两个集合的交集放入集合inter_zset
		
		
		// 区间 ：小于、大于
		
		
		// 求范围内的元素：
		
		
		// 区间：小于等于、大于等于
		
		
		// 求范围内的元素
		
		
		// 设置限制：限制返回4个，限制从第五个开始截取
		
		
		// 求区间内的元素，并使用限制
		
		
		// 求x4排行：排名第一返回0，排名第二返回1
		
		
		// 删除元素，返回删除个数，删除x5,x6
		
		
		// 按照排行删除从0开始算起，删除zset2排名第二和第三的元素
		
		
		// 获取所有集合的元素和分数，以-1代表全部
		
		
		// 删除指定的元素
		
		
		// 给集合中的一个元素加上11
		
		
		// 按照分数排序，删除指定范围内的元素，这里删除zset1中排名第二和第三的元素
		
		
		// 降序得set，并指定排行1->10的元素
		
		
	}
	
	/**
	 * 打印  TypedTuple 类型的Set 集合
	 * @param set
	 */
	public static void printTypedTuple(Set<TypedTuple> set) {
		if (set != null && set.isEmpty()) {
			return;
		}
		set.forEach(x -> {
			System.out.println("value:"+x.getValue()+", score"+x.getScore());
		});
		
	}
	
	/**
	 * 打印普通的Set 集合
	 * @param set
	 */
	public static void printSet(Set set) {
		if (set != null && set.isEmpty()) {
			return;
		}
		set.forEach(x -> {
			System.out.print(x + "\t");
		});
		
	}
	

}
