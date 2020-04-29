/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 预算单据号管理表
 * @Table:
 * BUDG_NO_MANAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgNoManage implements Serializable {

	
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
	 * 单据编码
	 */
	private String table_code;
	
	/**
	 * 单据名称
	 */
	private String table_name;
	
	/**
	 * 单据号前缀
	 */
	private String prefixe;
	
	/**
	 * 年份
	 */
	private String year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 最大流水号
	 */
	private Long max_no;
	
	/**
	 * 流水号位数
	 */
	private Long seq_no;
	

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
	* 设置 单据编码
	* @param value 
	*/
	public void setTable_code(String value) {
		this.table_code = value;
	}
	
	/**
	* 获取 单据编码
	* @return String
	*/
	public String getTable_code() {
		return this.table_code;
	}
	/**
	* 设置 单据名称
	* @param value 
	*/
	public void setTable_name(String value) {
		this.table_name = value;
	}
	
	/**
	* 获取 单据名称
	* @return String
	*/
	public String getTable_name() {
		return this.table_name;
	}
	/**
	* 设置 单据号前缀
	* @param value 
	*/
	public void setPrefixe(String value) {
		this.prefixe = value;
	}
	
	/**
	* 获取 单据号前缀
	* @return String
	*/
	public String getPrefixe() {
		return this.prefixe;
	}
	/**
	* 设置 年份
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年份
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 月份
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月份
	* @return String
	*/
	public String getMonth() {
		return this.month;
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
	/**
	* 设置 流水号位数
	* @param value 
	*/
	public void setSeq_no(Long value) {
		this.seq_no = value;
	}
	
	/**
	* 获取 流水号位数
	* @return Long
	*/
	public Long getSeq_no() {
		return this.seq_no;
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