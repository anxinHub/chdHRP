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
 * 医院医保额度控制
 * @Table:
 * BUDG_HOS_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgHosYbLimit implements Serializable {

	
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
	 * 年度
	 */
	private String year;
	
	/**
	 * 医保类型编码
	 */
	private String insurance_code;
	
	/**
	 * 本年支付额度
	 */
	private Double pay_limit;
	
	/**
	 * 控线增量比率
	 */
	private Double rate;
	
	/**
	 * 本年控制额度
	 */
	private Double control_limit;
	
	/**
	 * 备注
	 */
	private String remark;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 医保类型编码
	* @param value 
	*/
	public void setInsurance_code(String value) {
		this.insurance_code = value;
	}
	
	/**
	* 获取 医保类型编码
	* @return String
	*/
	public String getInsurance_code() {
		return this.insurance_code;
	}
	/**
	* 设置 本年支付额度
	* @param value 
	*/
	public void setPay_limit(Double value) {
		this.pay_limit = value;
	}
	
	/**
	* 获取 本年支付额度
	* @return Double
	*/
	public Double getPay_limit() {
		return this.pay_limit;
	}
	/**
	* 设置 控线增量比率
	* @param value 
	*/
	public void setRate(Double value) {
		this.rate = value;
	}
	
	/**
	* 获取 控线增量比率
	* @return Double
	*/
	public Double getRate() {
		return this.rate;
	}
	/**
	* 设置 本年控制额度
	* @param value 
	*/
	public void setControl_limit(Double value) {
		this.control_limit = value;
	}
	
	/**
	* 获取 本年控制额度
	* @return Double
	*/
	public Double getControl_limit() {
		return this.control_limit;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getRemark() {
		return this.remark;
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