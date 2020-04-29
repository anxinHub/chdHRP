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
 * 状态（STATE），“01新建、02已审核”
计划类型（PLAN_TYPE)：01年度计划02追加计划
 * @Table:
 * BUDG_CUT_EMP_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCutEmpPlan implements Serializable {

	
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
	 * 计划单号
	 */
	private String plan_code;
	
	/**
	 * 计划年度
	 */
	private String plan_year;
	
	/**
	 * 计划类型
	 */
	private String plan_type;
	
	/**
	 * 减员人数
	 */
	private Integer num;
	
	/**
	 * 申报说明
	 */
	private String explain;
	
	/**
	 * 申报人
	 */
	private Long maker;
	
	/**
	 * 申报日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 状态
	 */
	private String state;
	

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
	* 设置 计划单号
	* @param value 
	*/
	public void setPlan_code(String value) {
		this.plan_code = value;
	}
	
	/**
	* 获取 计划单号
	* @return String
	*/
	public String getPlan_code() {
		return this.plan_code;
	}
	/**
	* 设置 计划年度
	* @param value 
	*/
	public void setPlan_year(String value) {
		this.plan_year = value;
	}
	
	/**
	* 获取 计划年度
	* @return String
	*/
	public String getPlan_year() {
		return this.plan_year;
	}
	/**
	* 设置 计划类型
	* @param value 
	*/
	public void setPlan_type(String value) {
		this.plan_type = value;
	}
	
	/**
	* 获取 计划类型
	* @return String
	*/
	public String getPlan_type() {
		return this.plan_type;
	}
	/**
	* 设置 减员人数
	* @param value 
	*/
	public void setNum(Integer value) {
		this.num = value;
	}
	
	/**
	* 获取 减员人数
	* @return Integer
	*/
	public Integer getNum() {
		return this.num;
	}
	/**
	* 设置 申报说明
	* @param value 
	*/
	public void setExplain(String value) {
		this.explain = value;
	}
	
	/**
	* 获取 申报说明
	* @return String
	*/
	public String getExplain() {
		return this.explain;
	}
	/**
	* 设置 申报人
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 申报人
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 申报日期
	* @param value 
	*/
	public void setMake_date(Date value) {
		this.make_date = value;
	}
	
	/**
	* 获取 申报日期
	* @return Date
	*/
	public Date getMake_date() {
		return this.make_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(String value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return String
	*/
	public String getState() {
		return this.state;
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