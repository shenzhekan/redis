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
 *     ��ɫҵ�������Ӧ���˻���ļ�����
 * @author SZK
 *
 */
@Component("roleServiceImpl")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * ����������id��ѯ��ɫ��Ϣ
	 * ����ʹ�õ��ǣ�Cacheable
	 * ���ȵ������в�ѯkey:key_role_#id
	 * ͨ��value���û����������ͨ��key�����
	 * �������д��ھͷ��ػ������ݣ�������ʷ����õ�����,Ȼ�����ݴ��뻺����
	 */
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,  // ���뼶��
				   propagation=Propagation.REQUIRED)    // ������Ϊ
	@Cacheable(value = "redisCacheManager", key = "'redis_role_'+#id")
	public Role getRole(Long id) {
		Role role = new Role();
		role = roleMapper.getRole(id);
		System.out.println(role.toString());
		return role;
	}



	/**
	 * ����������idɾ��һ������
	 */
	@Override
	public int deleteRole(Long id) {
		return roleMapper.deleteRole(id);
	}



	/**
	 * ����������һ������
	 * ����ʹ�õ��ǣ�CachePut
	 * ��ֱ�������ݿ��в���һ������
	 * ͬ����redis�����в���һ������
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
	 * ����������һ������
	 * ʹ�õ��ǣ�CachePut
	 * �ڸ������ݵ�ͬʱ��ͬ�����»���
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
