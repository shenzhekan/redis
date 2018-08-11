package com.szk.service;

import java.util.List;

import com.szk.pojo.Role;

public interface RoleService {
	
	/**
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 */
	public Role getRole(Long id);
	
	/**
	 * 根据id删除角色信息
	 * @param id
	 * @return
	 */
	public int deleteRole(Long id);
	
	/**
	 * 插入一条角色信息
	 * @param role
	 * @return
	 */
	public Role insertRole(Role role);
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	
	/**
	 * 根据角色名称和备注查找角色信息
	 * @param roleName
	 * @param note
	 * @return
	 */
	public List<Role> listRoles(String roleName, String note);

}
