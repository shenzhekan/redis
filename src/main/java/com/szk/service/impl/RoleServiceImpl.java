package com.szk.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.szk.mapper.RoleMapper;
import com.szk.pojo.Role;
import com.szk.service.RoleService;

/**
 * description:
 *     角色业务操作（应用了缓存的技术）
 * @author SZK
 *
 */
@Component("roleServiceImpl")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * 方法：根据id查询角色信息
	 * 这里使用的是：Cacheable
	 * 会先到缓存中查询key:key_role_#id
	 * 通过value引用缓存管理器，通过key定义键
	 * 若缓存中存在就返回缓存数据，否则访问方法得到数据,然后将数据存入缓存中
	 */
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,  // 隔离级别
				   propagation=Propagation.REQUIRED)    // 传播行为
	@Cacheable(value = "redisCacheManager", key = "'redis_role_'+#id")
	public Role getRole(Long id) {
		Role role = new Role();
		role = roleMapper.getRole(id);
		System.out.println(role.toString());
		return role;
	}



	/**
	 * 方法：根据id删除一条数据
	 */
	@Override
	public int deleteRole(Long id) {
		return roleMapper.deleteRole(id);
	}



	/**
	 * 方法：插入一条数据
	 * 这里使用的是：CachePut
	 * 会直接向数据库中插入一条数据
	 * 同期向redis缓存中插入一条数据
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED,
				   propagation = Propagation.REQUIRED)
	@CachePut(value="redisCacheManager",key="'key_role_'+#result.id")
	public Role insertRole(Role role) {
		roleMapper.insertRole(role);
		return role;
	}

	/**
	 * 方法：更新一条数据
	 * 使用的是：CachePut
	 * 在更新数据的同时会同步更新缓存
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED,
					propagation = Propagation.REQUIRED)
	@CachePut(value="redisCacheManager", key="'redis_role_'+#role.id")
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}


	@Override
	public List<Role> listRoles(String roleName, String note) {
		
		return null;
	}

}
