package com.szk.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component("role")
public class Role implements Serializable{

	/**
	 *  这个Role类要使用redis缓存，所以需要实现序列化
	 *  注意，对象要想序列化，需要实现Serializable接口，并重写serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String roleName;
	private String note;


	public Role(){}
	
	public Role(long id, String roleName, String note) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.note = note;
	}

	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", note=" + note + "]";
	}
	
	

}
