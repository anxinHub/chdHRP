/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:晋级申请近三年休假表
 * HR_PROMOTION_LEAVE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrPromotionLeave implements Serializable {

	
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
	private String year;
	
	/**
	 * 
	 */
	private Integer emp_id;
	
	/**
	 * HR_EMP_ATTEND
	 */
	private String work_year;
	
	/**
	 * HR_EMP_ATTEND
	 */
	private Integer sick;
	
	/**
	 * HR_EMP_ATTEND
	 */
	private Integer maternity;
	
	/**
	 * HR_EMP_ATTEND
	 */
	private Integer casual;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public Long getGroup_id() {
	return group_id;
}
public void setGroup_id(Long group_id) {
	this.group_id = group_id;
}
public Long getHos_id() {
	return hos_id;
}
public void setHos_id(Long hos_id) {
	this.hos_id = hos_id;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public Integer getEmp_id() {
	return emp_id;
}
public void setEmp_id(Integer emp_id) {
	this.emp_id = emp_id;
}
public String getWork_year() {
	return work_year;
}
public void setWork_year(String work_year) {
	this.work_year = work_year;
}
public Integer getSick() {
	return sick;
}
public void setSick(Integer sick) {
	this.sick = sick;
}
public Integer getMaternity() {
	return maternity;
}
public void setMaternity(Integer maternity) {
	this.maternity = maternity;
}
public Integer getCasual() {
	return casual;
}
public void setCasual(Integer casual) {
	this.casual = casual;
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