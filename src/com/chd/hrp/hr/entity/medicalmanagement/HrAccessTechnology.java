/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_REC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrAccessTechnology implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Double group_id;
	
	/**
	 * 
	 */
	private Double hos_id;
	
	/**
	 * 
	 */
	private String app_no;
	
	/**
	 * 
	 */
	private Integer emp_id;
	private String emp_name;
	private String dept_name;
	
	/**
	 * 
	 */
	private String oper_name;
	
	/**
	 * 
	 */
	private String title_code;
	
	/**
	 * 01：初次申请 02：重新申请
	 */
	private String app_type;
	
	/**
	 * 
	 */
	private Date app_date;
	
	/**
	 * 
	 */
	private Date acce_date;
	
	/**
	 * 
	 */
	private Integer case_num;
	
	/**
	 * 0:新建 1:待准入 2:已准入 3:未通过
	 */
	private Integer state;
	private String state_name;
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
	* @param value 
	*/
	public void setGroup_id(Double value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Double value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setApp_no(String value) {
		this.app_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getApp_no() {
		return this.app_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setEmp_id(Integer value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOper_name(String value) {
		this.oper_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getOper_name() {
		return this.oper_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTitle_code(String value) {
		this.title_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTitle_code() {
		return this.title_code;
	}
	/**
	* 设置 01：初次申请 02：重新申请
	* @param value 
	*/
	public void setApp_type(String value) {
		this.app_type = value;
	}
	
	/**
	* 获取 01：初次申请 02：重新申请
	* @return String
	*/
	public String getApp_type() {
		return this.app_type;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setApp_date(Date value) {
		this.app_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getApp_date() {
		return this.app_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAcce_date(Date value) {
		this.acce_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getAcce_date() {
		return this.acce_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_num(Integer value) {
		this.case_num = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getCase_num() {
		return this.case_num;
	}
	/**
	* 设置 0:新建 1:待准入 2:已准入 3:未通过
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 0:新建 1:待准入 2:已准入 3:未通过
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
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

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
}