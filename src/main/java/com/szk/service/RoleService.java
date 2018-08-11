package com.szk.service;

import java.util.List;

import com.szk.pojo.Role;

public interface RoleService {
	
	/**
	 * ����id��ȡ��ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public Role getRole(Long id);
	
	/**
	 * ����idɾ����ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public int deleteRole(Long id);
	
	/**
	 * ����һ����ɫ��Ϣ
	 * @param role
	 * @return
	 */
	public Role insertRole(Role role);
	
	/**
	 * ���½�ɫ��Ϣ
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	
	/**
	 * ���ݽ�ɫ���ƺͱ�ע���ҽ�ɫ��Ϣ
	 * @param roleName
	 * @param note
	 * @return
	 */
	public List<Role> listRoles(String roleName, String note);

}
