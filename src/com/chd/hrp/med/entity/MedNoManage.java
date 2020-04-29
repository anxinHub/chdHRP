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
 * 08199 单据号管理表
 * @Table:
 * MED_NO_MANAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedNoManage implements Serializable {

	
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
	 * 单据表编码
	 */
	private String table_code;
	
	/**
	 * 单据前缀
	 */
	private String prefixe;
	
	/**
	 * 年月
	 */
	private String year_month;
	
	/**
	 * 最大流水号
	 */
	private Long max_no;
	
	
	/**
	 * 业务类型
	 */
	private String bus_type;
	/**
	 * 仓库
	 */
	private String store_alias;
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
	* 设置 单据表编码
	* @param value 
	*/
	public void setTable_code(String value) {
		this.table_code = value;
	}
	
	/**
	* 获取 单据表编码
	* @return String
	*/
	public String getTable_code() {
		return this.table_code;
	}
	/**
	* 设置 单据前缀
	* @param value 
	*/
	public void setPrefixe(String value) {
		this.prefixe = value;
	}
	
	/**
	* 获取 单据前缀
	* @return String
	*/
	public String getPrefixe() {
		return this.prefixe;
	}
	/**
	* 设置 年月
	* @param value 
	*/
	public void setYear_month(String value) {
		this.year_month = value;
	}
	
	/**
	* 获取 年月
	* @return String
	*/
	public String getYear_month() {
		return this.year_month;
	}
	/**
	* 设置 最大流水号
	* @param value 
	*/
	public void setMax_no(Long value) {
		this.max_no = value;
	}
	
	/**
	* 获取 最大流水号
	* @return Long
	*/
	public Long getMax_no() {
		return this.max_no;
	}
	
	public String getBus_type() {
		return bus_type;
	}

	public void setBus_type(String bus_type) {
		this.bus_type = bus_type;
	}

	public String getStore_alias() {
		return store_alias;
	}

	public void setStore_alias(String store_alias) {
		this.store_alias = store_alias;
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