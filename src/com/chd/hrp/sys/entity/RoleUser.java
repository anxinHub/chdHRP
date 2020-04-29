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


public class RoleUser implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Long group_id;
	private Long hos_id;
	private Long user_id;
	private Long role_id;

	public void setId(Long value) {
		this.id = value;
	}
		
	public Long getId() {
		return this.id;
	}
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
	public void setRole_id(Long value) {
		this.role_id = value;
	}
		
	public Long getRole_id() {
		return this.role_id;
	}
}