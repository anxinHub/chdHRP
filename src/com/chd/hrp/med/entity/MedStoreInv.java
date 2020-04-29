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
 * 08110 仓库材料信息
 * @Table:
 * MED_STORE_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedStoreInv implements Serializable {

	
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
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 药品材料ID
	 */
	private Long inv_id;
	
	/**
	 * 最低库存
	 */
	private Double low_limit;
	
	/**
	 * 库存基数
	 */
	private Double stock_secu;
	
	/**
	 * 最高库存
	 */
	private Double high_limit;
	
	/**
	 * 是否自动补货
	 */
	private Integer is_auto_supply;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Integer location_id;
	private String location_code;
	private String location_name;
	
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
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 药品材料ID
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 药品材料ID
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 最低库存
	* @param value 
	*/
	public void setLow_limit(Double value) {
		this.low_limit = value;
	}
	
	/**
	* 获取 最低库存
	* @return Double
	*/
	public Double getLow_limit() {
		return this.low_limit;
	}
	/**
	* 设置 库存基数
	* @param value 
	*/
	public void setStock_secu(Double value) {
		this.stock_secu = value;
	}
	
	/**
	* 获取 库存基数
	* @return Double
	*/
	public Double getStock_secu() {
		return this.stock_secu;
	}
	/**
	* 设置 最高库存
	* @param value 
	*/
	public void setHigh_limit(Double value) {
		this.high_limit = value;
	}
	
	/**
	* 获取 最高库存
	* @return Double
	*/
	public Double getHigh_limit() {
		return this.high_limit;
	}
	/**
	* 设置 是否自动补货
	* @param value 
	*/
	public void setIs_auto_supply(Integer value) {
		this.is_auto_supply = value;
	}
	
	/**
	* 获取 是否自动补货
	* @return Integer
	*/
	public Integer getIs_auto_supply() {
		return this.is_auto_supply;
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

	/**
	 * @return the location_id
	 */
	public Integer getLocation_id() {
		return location_id;
	}

	/**
	 * @param location_id the location_id to set
	 */
	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}

	/**
	 * @return the location_code
	 */
	public String getLocation_code() {
		return location_code;
	}

	/**
	 * @param location_code the location_code to set
	 */
	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	/**
	 * @return the location_name
	 */
	public String getLocation_name() {
		return location_name;
	}

	/**
	 * @param location_name the location_name to set
	 */
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	
}