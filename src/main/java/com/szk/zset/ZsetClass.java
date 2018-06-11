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
		// ��Ԫ�ز������򼯺�
		redisTemplate.opsForZSet().add("zset1", set1);
		redisTemplate.opsForZSet().add("zset2", set2);
		
		// ͳ����zset1�е�Ԫ������
		Long size = redisTemplate.opsForZSet().zCard("zset1");
		System.out.println("zset1���򼯺��е�Ԫ��������"+size);
		
		// �Ƿ���Ϊscore,��ô����ķ���������  3<=score<=6
		size = redisTemplate.opsForZSet().count("zset1", 3, 6);
		System.out.println("count�����������3->6֮���Ԫ�ظ�����"+size);
		
	    // ���±�1��ʼ��ȡ5��Ԫ��,���ǲ����ط���,ÿһ��Ԫ�ض���String
		Set set = redisTemplate.opsForZSet().range("zset1", 1, 5);
		System.out.print("range������ȡ��1 ��ʼ��5 ��Ԫ�أ�");
		printSet(set);
		
		// ��ȡ��������Ԫ��,���ҶԼ��ϰ���������,�����ط���,ÿһ��Ԫ���� TypedTuple
		set = redisTemplate.opsForZSet().rangeWithScores("zset1", 0, -1);
		System.out.print("rangeWithScore������ȡ����Ԫ��ֵ�ͷ�����");
		printTypedTuple(set);
		
		// ��zset1 ��zset2 �������ϵĽ������뼯��inter_zset
		redisTemplate.opsForZSet().intersectAndStore("zset1", "zset2", "inter_zset");
		set = redisTemplate.opsForZSet().range("inter_zset", 0, -1);
		System.out.print("intersectAndStore�����󽻼���");
		printSet(set);
		
		// ���� ��С�ڡ�����
		Range range = Range.range();
		range.lt("x8");
		range.gt("x1");
		
		// ��Χ�ڵ�Ԫ�أ�
		set = redisTemplate.opsForZSet().rangeByLex("zset1", range);
		System.out.print("rangeByLex������ȡ����Ԫ�ؼ���1��");
		printSet(set);
		
		// ���䣺С�ڵ��ڡ����ڵ���
		range.lte("x8");
		range.gte("x1");
		
		// ��Χ�ڵ�Ԫ��
		set = redisTemplate.opsForZSet().rangeByLex("zset1", range);
		System.out.print("rangeByLex������ȡ����Ԫ�ؼ���2��");
		printSet(set);
		
		// �������ƣ����Ʒ���4�������ƴӵ�2����ʼ��ȡ
		Limit limit = Limit.limit();
		limit.count(4);
		limit.offset(2);
		
		// �������ڵ�Ԫ�أ���ʹ������
		set = redisTemplate.opsForZSet().rangeByLex("zset1", range,limit);
		System.out.print("limit���ƻ�ȡ�ļ���Ԫ�أ�");
		printSet(set);
		
		// ��x4���У�������һ����0�������ڶ�����1
		Long rank = redisTemplate.opsForZSet().rank("zset1", "x4");
		System.out.println("rank������x4���У�"+rank);
		
		// ɾ��Ԫ�أ�����ɾ��������ɾ��x5,x6
		size = redisTemplate.opsForZSet().remove("zset1", "x5","x6");
		System.out.println("remove����ɾ��x5,x6,����ɾ��������"+size);
		
		// ��������ɾ����0��ʼ����ɾ��zset2�����ڶ��͵�����Ԫ��
		size = redisTemplate.opsForZSet().removeRange("zset2", 1, 2);
		System.out.println("removeRange����ɾ��zset2��ָ�������Ԫ�أ�����ɾ��������"+size);
		
		// ��ȡ���м��ϵ�Ԫ�غͷ�������-1����ȫ��
		set = redisTemplate.opsForZSet().rangeWithScores("zset2", 0, -1);
		System.out.print("rangeWithScores������ȡ���򼯺��е�����Ԫ�أ�");
		printTypedTuple(set);
		
		// �������е�һ��Ԫ�ؼ���11
		Double score = redisTemplate.opsForZSet().incrementScore("zset1", "x1", 11);
		System.out.println("incrementScore��������x1�ķ���ֵ��(��ֵ)"+score);
		
		// ���շ�������ɾ��ָ����Χ�ڵ�Ԫ�أ�����ɾ��zset1�������ڶ��͵�����Ԫ��
		size = redisTemplate.opsForZSet().removeRangeByScore("zset1", 1, 2);
		System.out.println("removeRangeByScore������ͨ����������Ȼ��ɾ������Ϊ1 ��2 ��Ԫ�أ�����ɾ��������"+size);
		
		// �����set����ָ������1->10��Ԫ��
		set = redisTemplate.opsForZSet().reverseRangeWithScores("zset2", 1, 10);
		System.out.println("reverseRangeWithScores�����������zset2��������1��10��Ԫ��");
		printTypedTuple(set);
		
		
	}
	
	/**
	 * ��ӡ  TypedTuple ���͵�Set ����
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
	 * ��ӡ��ͨ��Set ����
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
