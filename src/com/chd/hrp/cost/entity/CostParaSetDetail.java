/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 成本_分摊参数设置明细
 * @Table:
 * COST_PARA_SET_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class CostParaSetDetail implements Serializable {

	
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
	 * 统计年份
	 */
	private String acc_year;
	
	/**
	 * 统计月份
	 */
	private String acc_month;
	
	/**
	 * 科室性质
	 */
	private Integer natur_code;
	
	/**
	 * 分摊类型
	 */
	private String type_code;
	
	/**
	 * 成本项目ID
	 */
	private Integer cost_item_id;
	
	/**
	 * 成本项目变更ID
	 */
	private Integer cost_item_no;
	

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
	* 设置 统计年份
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 统计年份
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 统计月份
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 统计月份
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 科室性质
	* @param value 
	*/
	public void setNatur_code(Integer value) {
		this.natur_code = value;
	}
	
	/**
	* 获取 科室性质
	* @return Integer
	*/
	public Integer getNatur_code() {
		return this.natur_code;
	}
	/**
	* 设置 分摊类型
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 分摊类型
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 成本项目ID
	* @param value 
	*/
	public void setCost_item_id(Integer value) {
		this.cost_item_id = value;
	}
	
	/**
	* 获取 成本项目ID
	* @return Integer
	*/
	public Integer getCost_item_id() {
		return this.cost_item_id;
	}
	/**
	* 设置 成本项目变更ID
	* @param value 
	*/
	public void setCost_item_no(Integer value) {
		this.cost_item_no = value;
	}
	
	/**
	* 获取 成本项目变更ID
	* @return Integer
	*/
	public Integer getCost_item_no() {
		return this.cost_item_no;
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