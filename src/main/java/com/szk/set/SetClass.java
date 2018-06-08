package com.szk.set;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class SetClass {

	/**
	 * redis �еļ��� Set ��������ʾ��
	 */
	public static void test1() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		Set set = null;
		
		// ��Ԫ�ش��뼯��
		redisTemplate.boundSetOps("set1").add("v1", "v2", "v3", "v4", "v5", "v6");
		redisTemplate.boundSetOps("set2").add("v2", "v4", "v6", "v8");
		
		// �󼯺ϳ���
		Long scard = redisTemplate.opsForSet().size("set1");
		System.out.println("set1�ĳ���Ϊ��"+scard);
		
		// ��
		set = redisTemplate.opsForSet().difference("set1", "set2");
		print(set);
		
		// �󽻼�
		set = redisTemplate.opsForSet().intersect("set1", "set2");
		print(set);
		
		// �ж��Ƿ��Ǽ����е�Ԫ��
		boolean isMember = redisTemplate.opsForSet().isMember("set1", "v1");
		System.out.println("v1 �Ƿ��Ǽ��� set1 �е�Ԫ�أ�"+isMember);
		
		// ��ȡ����������Ԫ��
		set = redisTemplate.opsForSet().members("set1");
		print(set);
		
		// �Ӽ������������һ��Ԫ��
		String temp = (String) redisTemplate.opsForSet().pop("set1");
		System.out.println("�Ӽ���  set1 ���������һ��Ԫ�أ�"+temp);
		
		// �����ȡһ�����ϵ�Ԫ��
		temp = (String) redisTemplate.opsForSet().randomMember("set1");
		System.out.println("�Ӽ���  set1 ��������һ��Ԫ�أ�"+temp);
		
		// ������2������Ԫ��
		List list = redisTemplate.opsForSet().randomMembers("set1", 2);
		System.out.print("�Ӽ���  set1 �л������Ԫ�أ�");
		list.forEach(System.out::print);
		System.out.println();
		
		// ɾ��һ�����ϵ�Ԫ�أ����������Ƕ��
		redisTemplate.opsForSet().remove("set1", "v3");
		
		// ���������ϵĲ���
		set = redisTemplate.opsForSet().union("set1", "set2");
		print(set);
		
		// Ϊ֮��������������Ƚ�
		printSet(redisTemplate, "set1");
		printSet(redisTemplate, "set2");
		
		// ���������ϵĲ�������浽diff_set��
		redisTemplate.opsForSet().differenceAndStore("set1", "set2", "diff_set");
		printSet(redisTemplate, "diff_set");
		
		// ���������ϵĽ����������浽inter_set��
		redisTemplate.opsForSet().intersectAndStore("set1", "set2", "inter_set");
		printSet(redisTemplate, "inter_set");
		
		// ���������ϵĲ����������浽union_set��
		redisTemplate.opsForSet().unionAndStore("set1", "set2", "union_set");
		printSet(redisTemplate, "union_set");
		
	}
	
	/**
	 * ����ָ������  Set �е�����Ԫ��
	 * @param set
	 */
	public static void print(Set set) {
		System.out.print("�����е�ֵ�� ");
		set.forEach(x -> {
			System.out.print(x+" ");
		});
		System.out.println();
	}
	
	/**
	 * ����Redis��ָ������ Set �е�����Ԫ��
	 * @param redisTemplate
	 * @param set
	 */
	public static void printSet(RedisTemplate redisTemplate, String set) {
		Set set2 = new HashSet<>();
		set2 = redisTemplate.opsForSet().members(set);
		System.out.print("����  "+set+"  �е�Ԫ��Ϊ��");
		set2.forEach(x -> {
			System.out.print(x+" ");
		});
		System.out.println();
	}
	
}
