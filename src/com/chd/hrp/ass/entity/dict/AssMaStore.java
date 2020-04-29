/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 04107 库房附属表
 * @Table:
 * MAT_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMaStore implements Serializable {

	
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
	 * 库房ID
	 */
	private Long store_id;
	
	/**
	 * 是否货位管理
	 */
	private Integer is_location;
	
	/**
	 * 是否采购库房
	 */
	private Integer is_purchase;
	
	/**
	 * 是否代销库房
	 */
	private Integer is_com;
	
	/**
	 * 主管部门
	 */
	private Long dept_id;
	
	/**
	 * 保管员
	 */
	private Long manager;
	
	/**
	 * 会计
	 */
	private Long acc_emp;
	
	/**
	 * 采购员
	 */
	private Long stock_emp;
	
	/**
	 * 电话
	 */
	private String telephone;
	

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
	* 设置 库房ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 库房ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 是否货位管理
	* @param value 
	*/
	public void setIs_location(Integer value) {
		this.is_location = value;
	}
	
	/**
	* 获取 是否货位管理
	* @return Integer
	*/
	public Integer getIs_location() {
		return this.is_location;
	}
	/**
	* 设置 是否采购库房
	* @param value 
	*/
	public void setIs_purchase(Integer value) {
		this.is_purchase = value;
	}
	
	/**
	* 获取 是否采购库房
	* @return Integer
	*/
	public Integer getIs_purchase() {
		return this.is_purchase;
	}
	/**
	* 设置 是否代销库房
	* @param value 
	*/
	public void setIs_com(Integer value) {
		this.is_com = value;
	}
	
	/**
	* 获取 是否代销库房
	* @return Integer
	*/
	public Integer getIs_com() {
		return this.is_com;
	}
	/**
	* 设置 主管部门
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 主管部门
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 保管员
	* @param value 
	*/
	public void setManager(Long value) {
		this.manager = value;
	}
	
	/**
	* 获取 保管员
	* @return Long
	*/
	public Long getManager() {
		return this.manager;
	}
	/**
	* 设置 会计
	* @param value 
	*/
	public void setAcc_emp(Long value) {
		this.acc_emp = value;
	}
	
	/**
	* 获取 会计
	* @return Long
	*/
	public Long getAcc_emp() {
		return this.acc_emp;
	}
	/**
	* 设置 采购员
	* @param value 
	*/
	public void setStock_emp(Long value) {
		this.stock_emp = value;
	}
	
	/**
	* 获取 采购员
	* @return Long
	*/
	public Long getStock_emp() {
		return this.stock_emp;
	}
	/**
	* 设置 电话
	* @param value 
	*/
	public void setTelephone(String value) {
		this.telephone = value;
	}
	
	/**
	* 获取 电话
	* @return String
	*/
	public String getTelephone() {
		return this.telephone;
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