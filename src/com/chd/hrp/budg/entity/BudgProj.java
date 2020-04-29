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
 * 项目预算
 * @Table:
 * BUDG_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgProj implements Serializable {

	
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
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 预算金额
	 */
	private Double budg_amount;
	
	/**
	 * 到账金额
	 */
	private Double in_amount;
	
	/**
	 * 支出金额
	 */
	private Double cost_amount;
	
	/**
	 * 可用余额
	 */
	private Double usable_amount;
	
	/**
	 * 预算余额
	 */
	private Double remain_amount;
	
	/**
	 * 执行进度
	 */
	private Double rate;
	

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
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 资金来源ID
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源ID
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
	}
	/**
	* 设置 预算金额
	* @param value 
	*/
	public void setBudg_amount(Double value) {
		this.budg_amount = value;
	}
	
	/**
	* 获取 预算金额
	* @return Double
	*/
	public Double getBudg_amount() {
		return this.budg_amount;
	}
	/**
	* 设置 到账金额
	* @param value 
	*/
	public void setIn_amount(Double value) {
		this.in_amount = value;
	}
	
	/**
	* 获取 到账金额
	* @return Double
	*/
	public Double getIn_amount() {
		return this.in_amount;
	}
	/**
	* 设置 支出金额
	* @param value 
	*/
	public void setCost_amount(Double value) {
		this.cost_amount = value;
	}
	
	/**
	* 获取 支出金额
	* @return Double
	*/
	public Double getCost_amount() {
		return this.cost_amount;
	}
	/**
	* 设置 可用余额
	* @param value 
	*/
	public void setUsable_amount(Double value) {
		this.usable_amount = value;
	}
	
	/**
	* 获取 可用余额
	* @return Double
	*/
	public Double getUsable_amount() {
		return this.usable_amount;
	}
	/**
	* 设置 预算余额
	* @param value 
	*/
	public void setRemain_amount(Double value) {
		this.remain_amount = value;
	}
	
	/**
	* 获取 预算余额
	* @return Double
	*/
	public Double getRemain_amount() {
		return this.remain_amount;
	}
	/**
	* 设置 执行进度
	* @param value 
	*/
	public void setRate(Double value) {
		this.rate = value;
	}
	
	/**
	* 获取 执行进度
	* @return Double
	*/
	public Double getRate() {
		return this.rate;
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