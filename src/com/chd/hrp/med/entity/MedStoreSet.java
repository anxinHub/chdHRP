/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 08108 虚仓仓库设置
 * @Table:
 * MED_STORE_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedStoreSet implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 设置ID
	 */
	private Long set_id;
	
	/**
	 * 名称
	 */
	
	private String set_code;
	
	
	private Object set_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 设置ID
	* @param value 
	*/
	public void setSet_id(Long value) {
		this.set_id = value;
	}
	
	/**
	* 获取 设置ID
	* @return Long
	*/
	public Long getSet_id() {
		return this.set_id;
	}
	
	
	
	public void setSet_code(String set_code) {
		this.set_code = set_code;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getSet_code() {
		return set_code;
	}
	/**
	* 设置 名称
	* @param value 
	*/
	public void setSet_name(Object value) {
		this.set_name = value;
	}
	
	/**
	* 获取 名称
	* @return Object
	*/
	public Object getSet_name() {
		return this.set_name;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}