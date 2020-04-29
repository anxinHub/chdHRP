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
 * 开始年度和结束年度，只有两个取值为本年、上年。
 * @Table:
 * BUDG_AWARDS_GRANT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAwardsGrantMonth implements Serializable {

	
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
	 * 奖金项目编码
	 */
	private String awards_item_code;
	
	/**
	 * 序号
	 */
	private Long id;
	
	/**
	 * 发放月
	 */
	private String grant_month;
	
	/**
	 * 开始年度
	 */
	private String start_year;
	
	/**
	 * 开始月份
	 */
	private String start_month;
	
	/**
	 * 结束年度
	 */
	private String end_year;
	
	/**
	 * 结束月份
	 */
	private String end_month;
	

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
	* 设置 奖金项目编码
	* @param value 
	*/
	public void setAwards_item_code(String value) {
		this.awards_item_code = value;
	}
	
	/**
	* 获取 奖金项目编码
	* @return String
	*/
	public String getAwards_item_code() {
		return this.awards_item_code;
	}
	/**
	* 设置 序号
	* @param value 
	*/
	public void setId(Long value) {
		this.id = value;
	}
	
	/**
	* 获取 序号
	* @return Long
	*/
	public Long getId() {
		return this.id;
	}
	/**
	* 设置 发放月
	* @param value 
	*/
	public void setGrant_month(String value) {
		this.grant_month = value;
	}
	
	/**
	* 获取 发放月
	* @return String
	*/
	public String getGrant_month() {
		return this.grant_month;
	}
	/**
	* 设置 开始年度
	* @param value 
	*/
	public void setStart_year(String value) {
		this.start_year = value;
	}
	
	/**
	* 获取 开始年度
	* @return String
	*/
	public String getStart_year() {
		return this.start_year;
	}
	/**
	* 设置 开始月份
	* @param value 
	*/
	public void setStart_month(String value) {
		this.start_month = value;
	}
	
	/**
	* 获取 开始月份
	* @return String
	*/
	public String getStart_month() {
		return this.start_month;
	}
	/**
	* 设置 结束年度
	* @param value 
	*/
	public void setEnd_year(String value) {
		this.end_year = value;
	}
	
	/**
	* 获取 结束年度
	* @return String
	*/
	public String getEnd_year() {
		return this.end_year;
	}
	/**
	* 设置 结束月份
	* @param value 
	*/
	public void setEnd_month(String value) {
		this.end_month = value;
	}
	
	/**
	* 获取 结束月份
	* @return String
	*/
	public String getEnd_month() {
		return this.end_month;
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