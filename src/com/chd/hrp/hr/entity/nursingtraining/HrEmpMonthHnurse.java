/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.entity.nursingtraining;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 
 * @Table: HR_EMP_MONTH_HNURSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrEmpMonthHnurse implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;

	/**
	 * 
	 */
	private Long hos_id;

	/**
	 * 
	 */
	private Long emp_id;
	
	private String emp_code;
	
	private String emp_name;

	/**
	 * 
	 */
	private String year;

	/**
	 * 
	 */
	private String month;

	/**
	 * 
	 */
	private Double morality;

	/**
	 * 
	 */
	private Double quality;

	/**
	 * 
	 */
	private Double safety;

	/**
	 * 
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Long getEmp_id() {
		return this.emp_id;
	}
	
	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setYear(String value) {
		this.year = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getYear() {
		return this.year;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setMonth(String value) {
		this.month = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getMonth() {
		return this.month;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setMorality(Double value) {
		this.morality = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Double getMorality() {
		return this.morality;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setQuality(Double value) {
		this.quality = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Double getQuality() {
		return this.quality;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setSafety(Double value) {
		this.safety = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Double getSafety() {
		return this.safety;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
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