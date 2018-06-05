package com.szk.pojo;

import java.io.Serializable;

public class Role implements Serializable{

	/**
	 *  ע�⣬����Ҫ�����л�����Ҫʵ��Serializable�ӿڣ�����дserialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String roleName;
	private String note;
	public long getId() {
		return id;
	}
	
	public Role(long id, String roleName, String note) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.note = note;
	}

	public void setId(long id) {
		this.id = id;
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
