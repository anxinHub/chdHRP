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
 * 外拨经费到账
 * @Table:
 * BUDG_OUTSIDE_FUND_GET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgOutsideFundGet implements Serializable {

	
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
	 * 自增ID
	 */
	private Long record_id;
	
	/**
	 * 摘要
	 */
	private String remark;
	
	/**
	 * 到账日期
	 */
	private Date get_date;
	
	/**
	 * 收入科目
	 */
	private String income_subj;
	
	/**
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 项目变更ID
	 */
	private Long proj_no;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 到账金额
	 */
	private Double get_amount;
	

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
	* 设置 自增ID
	* @param value 
	*/
	public void setRecord_id(Long value) {
		this.record_id = value;
	}
	
	/**
	* 获取 自增ID
	* @return Long
	*/
	public Long getRecord_id() {
		return this.record_id;
	}
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getRemark() {
		return this.remark;
	}
	/**
	* 设置 到账日期
	* @param value 
	*/
	public void setGet_date(Date value) {
		this.get_date = value;
	}
	
	/**
	* 获取 到账日期
	* @return Date
	*/
	public Date getGet_date() {
		return this.get_date;
	}
	/**
	* 设置 收入科目
	* @param value 
	*/
	public void setIncome_subj(String value) {
		this.income_subj = value;
	}
	
	/**
	* 获取 收入科目
	* @return String
	*/
	public String getIncome_subj() {
		return this.income_subj;
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
	* 设置 项目变更ID
	* @param value 
	*/
	public void setProj_no(Long value) {
		this.proj_no = value;
	}
	
	/**
	* 获取 项目变更ID
	* @return Long
	*/
	public Long getProj_no() {
		return this.proj_no;
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
	* 设置 到账金额
	* @param value 
	*/
	public void setGet_amount(Double value) {
		this.get_amount = value;
	}
	
	/**
	* 获取 到账金额
	* @return Double
	*/
	public Double getGet_amount() {
		return this.get_amount;
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