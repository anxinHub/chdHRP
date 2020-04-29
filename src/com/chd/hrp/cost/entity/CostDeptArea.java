/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 科室面积表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostDeptArea implements Serializable {

	
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
	 * t统计年月
	 */
	private String year_month;
	private String acc_year;
	private String acc_month;
	/**
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	/**
	 * 科室编码
	 */
	private String dept_code;
	/**
	 * 科室名称
	 */
	private String dept_name;
	/**
	 * 面积
	 */
	private double arear;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	
	/**
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}
	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	/**
	 * @return the acc_month
	 */
	public String getAcc_month() {
		return acc_month;
	}
	/**
	 * @param acc_month the acc_month to set
	 */
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 面积
	 */
	public void setArear(double value) {
		this.arear = value;
	}
	/**
	 * 获取 面积
	 */	
	public double getArear() {
		return this.arear;
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
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the year_month
	 */
	public String getYear_month() {
		return year_month;
	}
	/**
	 * @param year_month the year_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	
}