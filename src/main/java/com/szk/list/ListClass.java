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
	 *  Redis 基本结构操作示例
	 */
	public static void ListTest1() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		// 删除链表，以用于反复测试
		redisTemplate.delete("list");
		
		// 把 node3 插入链表list
		redisTemplate.opsForList().leftPush("list", "node");
		
		// 相当于 lpush 把多个值插入list: 用List类型保存多个值
		List<String> nodeList = new ArrayList<String>();
		nodeList.add("node2");
		nodeList.add("node1");
		redisTemplate.opsForList().leftPushAll("list", nodeList);
		
		// 从右边插入一个节点
		redisTemplate.opsForList().rightPush("list", "node3");
		
		// 获取下标为 0 的节点
		String node1 = (String) redisTemplate.opsForList().index("list", 0);
		System.out.println("get the index of 0 :"+node1);
		
		// 获取链表长度
		Long llen = redisTemplate.opsForList().size("list");
		System.out.println("the length of list :"+llen);
		
		// 从左边弹出一个节点
		String node = (String) redisTemplate.opsForList().leftPop("list");
		System.out.println("Pop from the left :"+node);
		
		// 从右边弹出一个节点
		String node2 = (String) redisTemplate.opsForList().rightPop("list");
		System.out.println("Pop from the right :"+node2);
		
		// linsert命令在node2前插入一个节点
		// 注意，需要使用更为底层的命令才能操作linsert命令
		try {
			redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8")
					, RedisListCommands.Position.BEFORE
					, "node2".getBytes("utf-8")
					, "before_node".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("the error from lInsert():before");
			e.printStackTrace();
		}
		
		// 使用linsert 命令在node2后插入一个节点
		try {
			redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8")
					, RedisListCommands.Position.AFTER
					, "node2".getBytes("utf-8")
					, "after_node".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("the error from lInsert():after");
			e.printStackTrace();
		}
		
		// 判断list是否存在，如果存在则从左边插入head节点
		redisTemplate.opsForList().leftPushIfPresent("list", "head");
		
		// 判断list是否存在，如果存在则从右边插入end 节点
		redisTemplate.opsForList().rightPushIfPresent("list", "end");
		
		// 从左到右，或者下标从0到10的节点元素:返回的是List类型
		List<String> rangeList = redisTemplate.opsForList().range("list", 0, 10);
		System.out.println("rangList: "+rangeList);
		
		// 在链表左边插入三个值为node的节点
		redisTemplate.opsForList().leftPushAll("list", "node","node","node");
		
		// 从左到右删除至多2个node节点
		redisTemplate.opsForList().remove("list", 2, "node");
		
		// 给链表下标为0 的节点设置新值
		redisTemplate.opsForList().set("list", 0, "new_head_node");
		
		// 打印链表数据
		printList(redisTemplate, "list");
	}
	
	/**
	 *  Redis 阻塞命令的操作示例
	 */
	public static void test2() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
		
		redisTemplate.delete("list1");
		redisTemplate.delete("list2");
		
		// 初始化链表  list1
		List<String> newList1 = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			newList1.add("node"+i);
		}
		redisTemplate.opsForList().leftPushAll("list1", newList1);
		
		// Spring使用参数超时时间作为阻塞命令区分，等价于  blpop 命令，并且可以设置时间参数
		redisTemplate.opsForList().leftPop("list1", 3, TimeUnit.SECONDS);
		
		// 同上，相当于  brpop 命令
		redisTemplate.opsForList().rightPop("list1", 3, TimeUnit.SECONDS);
		
		newList1.clear();
		// 初始化链表 list2
		for (int i = 0; i < 5; i++) {
			newList1.add("data"+i);
		}
		redisTemplate.opsForList().leftPushAll("list2", newList1);
		
		// 相当于 rpoplpush 命令
		redisTemplate.opsForList().rightPopAndLeftPush("list1", "list2");
		
		// 相当于 brpoplpush 命令    : 会给所用的链表加锁
		redisTemplate.opsForList().rightPopAndLeftPush("list1", "list2", 3, TimeUnit.SECONDS);
		
		// 打印链表
		printList(redisTemplate, "list1");
		printList(redisTemplate, "list2");
		
	}
	
	/**
	 * 打印指定链表的信息
	 * @param redisTemplate
	 */
	public static void printList(RedisTemplate redisTemplate, String key) {
		Long size = redisTemplate.opsForList().size(key);
		List<String> valueList = (List<String>) redisTemplate.opsForList().range(key, 0, size);
		System.out.println(valueList);
	}

}
