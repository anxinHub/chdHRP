/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class UserPerm implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long user_id;
	private String perm_code;
	private String mod_code;
	private String copy_code;

	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
		
	public Long getHos_id() {
		return this.hos_id;
	}
	public void setUser_id(Long value) {
		this.user_id = value;
	}
		
	public Long getUser_id() {
		return this.user_id;
	}
	public void setPerm_code(String value) {
		this.perm_code = value;
	}
		
	public String getPerm_code() {
		return this.perm_code;
	}
	public void setMod_code(String value) {
		this.mod_code = value;
	}
		
	public String getMod_code() {
		return this.mod_code;
	}

	
	/**
	 * 获取 copy_code
	 * @return copy_code
	 */
	public String getCopy_code() {
		return copy_code;
	}

	
	/**
	 * 设置 copy_code
	 * @param copy_code 
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
}