package com.szk.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.szk.pojo.Role;

/**
 * description:
 * 	角色映射的方法接口
 * @author SZK
 *
 */
@Repository
public interface RoleMapper {

	/**
	 * 根据id,获取角色信息
	 * @param id
	 * @return
	 */
	public Role getRole(Long id);
	
	/**
	 * 根据id,删除指定的角色信息
	 * @param id
	 * @return
	 */
	public int deleteRole(Long id);
	
	/**
	 * 添加一个角色信息
	 * @param role
	 * @return
	 */
	public int insertRole(Role role);
	
	/**
	 * 更新一个角色的信息
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);

	/**
	 * 根据 角色名称或者备注  （模糊查询）  ，来查询具体的角色信息
	 * @param roleName
	 * @param note
	 * @return
	 */
	public List<Role> listRoles(@Param("roleName") String roleName, @Param("note") String note);
}