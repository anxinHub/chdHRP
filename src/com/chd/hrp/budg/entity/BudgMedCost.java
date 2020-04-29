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
 * 医疗支出预算
 * @Table:
 * BUDG_MED_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedCost implements Serializable {

	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 月
	 */
	private String month;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	private String dept_code;
	
	private String dept_name;
	
	private String summary;
	
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 科目编码
	 */
	private String subj_code;
	
	private String subj_name;
	
	/**
	 * 支出预算
	 */
	private Double cost_budg;
	
	/**
	 * 上月结转
	 */
	private Double last_month_carried;
	
	/**
	 * 结转下月
	 */
	private Double carried_next_month;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	private String month_data1;
	private String month_data2;
	private String month_data3;
	private String month_data4;
	private String month_data5;
	private String month_data6;
	private String month_data7;
	private String month_data8;
	private String month_data9;
	private String month_data10;
	private String month_data11;
	private String month_data12;
	
	
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

	public String getSubj_name() {
		return subj_name;
	}

	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}

	public String getMonth_data1() {
		return month_data1;
	}

	public void setMonth_data1(String month_data1) {
		this.month_data1 = month_data1;
	}

	public String getMonth_data2() {
		return month_data2;
	}

	public void setMonth_data2(String month_data2) {
		this.month_data2 = month_data2;
	}

	public String getMonth_data3() {
		return month_data3;
	}

	public void setMonth_data3(String month_data3) {
		this.month_data3 = month_data3;
	}

	public String getMonth_data4() {
		return month_data4;
	}

	public void setMonth_data4(String month_data4) {
		this.month_data4 = month_data4;
	}

	public String getMonth_data5() {
		return month_data5;
	}

	public void setMonth_data5(String month_data5) {
		this.month_data5 = month_data5;
	}

	public String getMonth_data6() {
		return month_data6;
	}

	public void setMonth_data6(String month_data6) {
		this.month_data6 = month_data6;
	}

	public String getMonth_data7() {
		return month_data7;
	}

	public void setMonth_data7(String month_data7) {
		this.month_data7 = month_data7;
	}

	public String getMonth_data8() {
		return month_data8;
	}

	public void setMonth_data8(String month_data8) {
		this.month_data8 = month_data8;
	}

	public String getMonth_data9() {
		return month_data9;
	}

	public void setMonth_data9(String month_data9) {
		this.month_data9 = month_data9;
	}

	public String getMonth_data10() {
		return month_data10;
	}

	public void setMonth_data10(String month_data10) {
		this.month_data10 = month_data10;
	}

	public String getMonth_data11() {
		return month_data11;
	}

	public void setMonth_data11(String month_data11) {
		this.month_data11 = month_data11;
	}

	public String getMonth_data12() {
		return month_data12;
	}

	public void setMonth_data12(String month_data12) {
		this.month_data12 = month_data12;
	}

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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 月
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月
	* @return String
	*/
	public String getMonth() {
		return this.month;
	}
	/**
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 科目编码
	* @param value 
	*/
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	
	/**
	* 获取 科目编码
	* @return String
	*/
	public String getSubj_code() {
		return this.subj_code;
	}
	/**
	* 设置 支出预算
	* @param value 
	*/
	public void setCost_budg(Double value) {
		this.cost_budg = value;
	}
	
	/**
	* 获取 支出预算
	* @return Double
	*/
	public Double getCost_budg() {
		return this.cost_budg;
	}
	/**
	* 设置 上月结转
	* @param value 
	*/
	public void setLast_month_carried(Double value) {
		this.last_month_carried = value;
	}
	
	/**
	* 获取 上月结转
	* @return Double
	*/
	public Double getLast_month_carried() {
		return this.last_month_carried;
	}
	/**
	* 设置 结转下月
	* @param value 
	*/
	public void setCarried_next_month(Double value) {
		this.carried_next_month = value;
	}
	
	/**
	* 获取 结转下月
	* @return Double
	*/
	public Double getCarried_next_month() {
		return this.carried_next_month;
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