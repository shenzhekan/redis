package com.szk.zset;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
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
		
		// 统计在zset1中的元素总数
		Long size = redisTemplate.opsForZSet().zCard("zset1");
		System.out.println("zset1有序集合中的元素总数："+size);
		
		// 记分数为score,那么下面的方法就是求  3<=score<=6
		size = redisTemplate.opsForZSet().count("zset1", 3, 6);
		System.out.println("count函数求分数在3->6之间的元素个数："+size);
		
	    // 从下标1开始截取5个元素,但是不返回分数,每一个元素都是String
		Set set = redisTemplate.opsForZSet().range("zset1", 1, 5);
		System.out.print("range函数截取从1 开始的5 个元素：");
		printSet(set);
		
		// 截取集合所有元素,并且对集合按分数排序,并返回分数,每一个元素是 TypedTuple
		set = redisTemplate.opsForZSet().rangeWithScores("zset1", 0, -1);
		System.out.print("rangeWithScore函数获取所有元素值和分数：");
		printTypedTuple(set);
		
		// 将zset1 和zset2 两个集合的交集放入集合inter_zset
		redisTemplate.opsForZSet().intersectAndStore("zset1", "zset2", "inter_zset");
		set = redisTemplate.opsForZSet().range("inter_zset", 0, -1);
		System.out.print("intersectAndStore函数求交集：");
		printSet(set);
		
		// 区间 ：小于、大于
		Range range = Range.range();
		range.lt("x8");
		range.gt("x1");
		
		// 求范围内的元素：
		set = redisTemplate.opsForZSet().rangeByLex("zset1", range);
		System.out.print("rangeByLex函数获取区间元素集合1：");
		printSet(set);
		
		// 区间：小于等于、大于等于
		range.lte("x8");
		range.gte("x1");
		
		// 求范围内的元素
		set = redisTemplate.opsForZSet().rangeByLex("zset1", range);
		System.out.print("rangeByLex函数获取区间元素集合2：");
		printSet(set);
		
		// 设置限制：限制返回4个，限制从第2个开始截取
		Limit limit = Limit.limit();
		limit.count(4);
		limit.offset(2);
		
		// 求区间内的元素，并使用限制
		set = redisTemplate.opsForZSet().rangeByLex("zset1", range,limit);
		System.out.print("limit限制获取的集合元素：");
		printSet(set);
		
		// 求x4排行：排名第一返回0，排名第二返回1
		Long rank = redisTemplate.opsForZSet().rank("zset1", "x4");
		System.out.println("rank函数求x4排行："+rank);
		
		// 删除元素，返回删除个数，删除x5,x6
		size = redisTemplate.opsForZSet().remove("zset1", "x5","x6");
		System.out.println("remove函数删除x5,x6,返回删除个数："+size);
		
		// 按照排行删除从0开始算起，删除zset2排名第二和第三的元素
		size = redisTemplate.opsForZSet().removeRange("zset2", 1, 2);
		System.out.println("removeRange函数删除zset2中指定排序的元素，返回删除个数："+size);
		
		// 获取所有集合的元素和分数，以-1代表全部
		set = redisTemplate.opsForZSet().rangeWithScores("zset2", 0, -1);
		System.out.print("rangeWithScores函数获取有序集合中的所有元素：");
		printTypedTuple(set);
		
		// 给集合中的一个元素加上11
		Double score = redisTemplate.opsForZSet().incrementScore("zset1", "x1", 11);
		System.out.println("incrementScore函数增加x1的分数值：(新值)"+score);
		
		// 按照分数排序，删除指定范围内的元素，这里删除zset1中排名第二和第三的元素
		size = redisTemplate.opsForZSet().removeRangeByScore("zset1", 1, 2);
		System.out.println("removeRangeByScore函数先通过分数排序，然后删除排名为1 和2 的元素，返回删除个数："+size);
		
		// 降序得set，并指定排行1->10的元素
		set = redisTemplate.opsForZSet().reverseRangeWithScores("zset2", 1, 10);
		System.out.println("reverseRangeWithScores函数求得逆序zset2中排名从1到10的元素");
		printTypedTuple(set);
		
		
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
