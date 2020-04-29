/**
 * 2014-5-20 SysMenu.java author:pengjin
 */
package com.chd.hrp.sys.entity;

public class SysMenu  implements Comparable<SysMenu>{

	private static final long serialVersionUID = 1L;

	private int id;

	private int pid;

	private String menu_name;

	private String menu_url;

	private boolean is_accordion;

	private String perm_id;

	private String mod_code;

	private String ischecked;
	private String code;
	
	private String is_view;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public boolean isIs_accordion() {
		return is_accordion;
	}

	public void setIs_accordion(boolean is_accordion) {
		this.is_accordion = is_accordion;
	}

	public String getPerm_id() {
		return perm_id;
	}

	public void setPerm_id(String perm_id) {
		this.perm_id = perm_id;
	}

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIschecked() {
		return ischecked;
	}

	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}

	
	/**
	 * 获取 code
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	
	/**
	 * 设置 code
	 * @param code 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int compareTo(SysMenu o) {
		SysMenu st=o;
		return this.id - st.id;
	}

	
	/**
	 * 获取 is_view
	 * @return is_view
	 */
	public String getIs_view() {
		return is_view;
	}

	
	/**
	 * 设置 is_view
	 * @param is_view 
	 */
	public void setIs_view(String is_view) {
		this.is_view = is_view;
	}

}
