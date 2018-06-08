package com.szk.list;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.RedisTemplate;

public class ListClass {
	
	/**
	 *  Redis �����ṹ����ʾ��
	 */
	public static void ListTest1() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		// ɾ�����������ڷ�������
		redisTemplate.delete("list");
		
		// �� node3 ��������list
		redisTemplate.opsForList().leftPush("list", "node");
		
		// �൱�� lpush �Ѷ��ֵ����list: ��List���ͱ�����ֵ
		List<String> nodeList = new ArrayList<String>();
		nodeList.add("node2");
		nodeList.add("node1");
		redisTemplate.opsForList().leftPushAll("list", nodeList);
		
		// ���ұ߲���һ���ڵ�
		redisTemplate.opsForList().rightPush("list", "node3");
		
		// ��ȡ�±�Ϊ 0 �Ľڵ�
		String node1 = (String) redisTemplate.opsForList().index("list", 0);
		System.out.println("get the index of 0 :"+node1);
		
		// ��ȡ������
		Long llen = redisTemplate.opsForList().size("list");
		System.out.println("the length of list :"+llen);
		
		// ����ߵ���һ���ڵ�
		String node = (String) redisTemplate.opsForList().leftPop("list");
		System.out.println("Pop from the left :"+node);
		
		// ���ұߵ���һ���ڵ�
		String node2 = (String) redisTemplate.opsForList().rightPop("list");
		System.out.println("Pop from the right :"+node2);
		
		// linsert������node2ǰ����һ���ڵ�
		// ע�⣬��Ҫʹ�ø�Ϊ�ײ��������ܲ���linsert����
		try {
			redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8")
					, RedisListCommands.Position.BEFORE
					, "node2".getBytes("utf-8")
					, "before_node".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("the error from lInsert():before");
			e.printStackTrace();
		}
		
		// ʹ��linsert ������node2�����һ���ڵ�
		try {
			redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8")
					, RedisListCommands.Position.AFTER
					, "node2".getBytes("utf-8")
					, "after_node".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("the error from lInsert():after");
			e.printStackTrace();
		}
		
		// �ж�list�Ƿ���ڣ�������������߲���head�ڵ�
		redisTemplate.opsForList().leftPushIfPresent("list", "head");
		
		// �ж�list�Ƿ���ڣ������������ұ߲���end �ڵ�
		redisTemplate.opsForList().rightPushIfPresent("list", "end");
		
		// �����ң������±��0��10�Ľڵ�Ԫ��:���ص���List����
		List<String> rangeList = redisTemplate.opsForList().range("list", 0, 10);
		System.out.println("rangList: "+rangeList);
		
		// ��������߲�������ֵΪnode�Ľڵ�
		redisTemplate.opsForList().leftPushAll("list", "node","node","node");
		
		// ������ɾ������2��node�ڵ�
		redisTemplate.opsForList().remove("list", 2, "node");
		
		// �������±�Ϊ0 �Ľڵ�������ֵ
		redisTemplate.opsForList().set("list", 0, "new_head_node");
		
		// ��ӡ��������
		printList(redisTemplate, "list");
	}
	
	/**
	 *  Redis ��������Ĳ���ʾ��
	 */
	public static void test2() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		redisTemplate.delete("list1");
		redisTemplate.delete("list2");
		
		// ��ʼ������  list1
		List<String> newList1 = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			newList1.add("node"+i);
		}
		redisTemplate.opsForList().leftPushAll("list1", newList1);
		
		// Springʹ�ò�����ʱʱ����Ϊ�����������֣��ȼ���  blpop ������ҿ�������ʱ�����
		redisTemplate.opsForList().leftPop("list1", 3, TimeUnit.SECONDS);
		
		// ͬ�ϣ��൱��  brpop ����
		redisTemplate.opsForList().rightPop("list1", 3, TimeUnit.SECONDS);
		
		newList1.clear();
		// ��ʼ������ list2
		for (int i = 0; i < 5; i++) {
			newList1.add("data"+i);
		}
		redisTemplate.opsForList().leftPushAll("list2", newList1);
		
		// �൱�� rpoplpush ����
		redisTemplate.opsForList().rightPopAndLeftPush("list1", "list2");
		
		// �൱�� brpoplpush ����    : ������õ��������
		redisTemplate.opsForList().rightPopAndLeftPush("list1", "list2", 3, TimeUnit.SECONDS);
		
		// ��ӡ����
		printList(redisTemplate, "list1");
		printList(redisTemplate, "list2");
		
	}
	
	/**
	 * ��ӡָ���������Ϣ
	 * @param redisTemplate
	 */
	public static void printList(RedisTemplate redisTemplate, String key) {
		Long size = redisTemplate.opsForList().size(key);
		List<String> valueList = (List<String>) redisTemplate.opsForList().range(key, 0, size);
		System.out.println(valueList);
	}

}
