package com.chd.hrp.sys.entity;

public class UserPermData {
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private Long user_id;
	private Long role_id;
	private String table_code;
	private String perm_code;
	private String mod_code;
	private Long is_read;
	private Long is_write;
	
	
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getTable_code() {
		return table_code;
	}
	public void setTable_code(String table_code) {
		this.table_code = table_code;
	}
	public String getPerm_code() {
		return perm_code;
	}
	public void setPerm_code(String perm_code) {
		this.perm_code = perm_code;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public Long getIs_read() {
		return is_read;
	}
	public void setIs_read(Long is_read) {
		this.is_read = is_read;
	}
	public Long getIs_write() {
		return is_write;
	}
	public void setIs_write(Long is_write) {
		this.is_write = is_write;
	}
	
}
