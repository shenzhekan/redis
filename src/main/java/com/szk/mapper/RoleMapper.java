package com.szk.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.szk.pojo.Role;

/**
 * description:
 * 	��ɫӳ��ķ����ӿ�
 * @author SZK
 *
 */
@Repository
public interface RoleMapper {

	/**
	 * ����id,��ȡ��ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public Role getRole(Long id);
	
	/**
	 * ����id,ɾ��ָ���Ľ�ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public int deleteRole(Long id);
	
	/**
	 * ���һ����ɫ��Ϣ
	 * @param role
	 * @return
	 */
	public int insertRole(Role role);
	
	/**
	 * ����һ����ɫ����Ϣ
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);

	/**
	 * ���� ��ɫ���ƻ��߱�ע  ��ģ����ѯ��  ������ѯ����Ľ�ɫ��Ϣ
	 * @param roleName
	 * @param note
	 * @return
	 */
	public List<Role> listRoles(@Param("roleName") String roleName, @Param("note") String note);
}