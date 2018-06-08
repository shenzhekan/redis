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
		// ��Ԫ�ز������򼯺�
		redisTemplate.opsForZSet().add("zset1", set1);
		redisTemplate.opsForZSet().add("zset2", set2);
		
		// ͳ����set1�е�Ԫ������
		
		
		// �Ƿ���Ϊscore,��ô����ķ���������  3<=score<=6
		
		
	    // ���±�1��ʼ��ȡ5��Ԫ��,���ǲ����ط���,ÿһ��Ԫ�ض���String
		
		
		// ��ȡ��������Ԫ��,���ҶԼ��ϰ���������,�����ط���,ÿһ��Ԫ���� TypedTuple
		
		
		// ��zset1 ��zset2 �������ϵĽ������뼯��inter_zset
		
		
		// ���� ��С�ڡ�����
		
		
		// ��Χ�ڵ�Ԫ�أ�
		
		
		// ���䣺С�ڵ��ڡ����ڵ���
		
		
		// ��Χ�ڵ�Ԫ��
		
		
		// �������ƣ����Ʒ���4�������ƴӵ������ʼ��ȡ
		
		
		// �������ڵ�Ԫ�أ���ʹ������
		
		
		// ��x4���У�������һ����0�������ڶ�����1
		
		
		// ɾ��Ԫ�أ�����ɾ��������ɾ��x5,x6
		
		
		// ��������ɾ����0��ʼ����ɾ��zset2�����ڶ��͵�����Ԫ��
		
		
		// ��ȡ���м��ϵ�Ԫ�غͷ�������-1����ȫ��
		
		
		// ɾ��ָ����Ԫ��
		
		
		// �������е�һ��Ԫ�ؼ���11
		
		
		// ���շ�������ɾ��ָ����Χ�ڵ�Ԫ�أ�����ɾ��zset1�������ڶ��͵�����Ԫ��
		
		
		// �����set����ָ������1->10��Ԫ��
		
		
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
