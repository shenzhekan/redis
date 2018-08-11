package com.szk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.szk.pojo.Role;

@Repository
public interface RoleDao {

	public Role getRole(Long id);
	
	public int deleteRole(Long id);
	
	public int insertRole(Role role);
	
	public int updateRole(Role role);

	public List<Role> listRoles(@Param("roleName") String roleName, @Param("note") String note);
}
