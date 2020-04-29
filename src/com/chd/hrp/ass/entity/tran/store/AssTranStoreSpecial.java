/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.tran.store;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050901 资产转移主表(仓库到仓库)_专用设备
 * @Table:
 * ASS_TRAN_STORE_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssTranStoreSpecial implements Serializable {

	
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
	 * 资产转移单号
	 */
	private String tran_no;
	
	/**
	 * 移出仓库ID
	 */
	private Long out_store_id;
	
	/**
	 * 移出仓库变更NO
	 */
	private Long out_store_no;
	
	private String out_store_code;
	
	private String out_store_name;
	
	/**
	 * 移入仓库ID
	 */
	private Long in_store_id;
	
	/**
	 * 移入仓库变更NO
	 */
	private Long in_store_no;
	
	private String in_store_code;
	
	private String in_store_name;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	
	private String create_emp_name;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	
	private String audit_emp_name;
	
	/**
	 * 审核日期
	 */
	private Date audit_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	private String state_name;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public String getOut_store_code() {
		return out_store_code;
	}
	
	public void setOut_store_code(String out_store_code) {
		this.out_store_code = out_store_code;
	}
	
	public String getOut_store_name() {
		return out_store_name;
	}
	
	public void setOut_store_name(String out_store_name) {
		this.out_store_name = out_store_name;
	}
	
	public String getIn_store_code() {
		return in_store_code;
	}
	
	public void setIn_store_code(String in_store_code) {
		this.in_store_code = in_store_code;
	}
	
	public String getIn_store_name() {
		return in_store_name;
	}
	
	public void setIn_store_name(String in_store_name) {
		this.in_store_name = in_store_name;
	}
	
	public String getCreate_emp_name() {
		return create_emp_name;
	}
	
	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}
	
	public String getAudit_emp_name() {
		return audit_emp_name;
	}
	
	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}
	
	public String getState_name() {
		return state_name;
	}
	
	public void setState_name(String state_name) {
		this.state_name = state_name;
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
	* 设置 资产转移单号
	* @param value 
	*/
	public void setTran_no(String value) {
		this.tran_no = value;
	}
	
	/**
	* 获取 资产转移单号
	* @return String
	*/
	public String getTran_no() {
		return this.tran_no;
	}
	/**
	* 设置 移出仓库ID
	* @param value 
	*/
	public void setOut_store_id(Long value) {
		this.out_store_id = value;
	}
	
	/**
	* 获取 移出仓库ID
	* @return Long
	*/
	public Long getOut_store_id() {
		return this.out_store_id;
	}
	/**
	* 设置 移出仓库变更NO
	* @param value 
	*/
	public void setOut_store_no(Long value) {
		this.out_store_no = value;
	}
	
	/**
	* 获取 移出仓库变更NO
	* @return Long
	*/
	public Long getOut_store_no() {
		return this.out_store_no;
	}
	/**
	* 设置 移入仓库ID
	* @param value 
	*/
	public void setIn_store_id(Long value) {
		this.in_store_id = value;
	}
	
	/**
	* 获取 移入仓库ID
	* @return Long
	*/
	public Long getIn_store_id() {
		return this.in_store_id;
	}
	/**
	* 设置 移入仓库变更NO
	* @param value 
	*/
	public void setIn_store_no(Long value) {
		this.in_store_no = value;
	}
	
	/**
	* 获取 移入仓库变更NO
	* @return Long
	*/
	public Long getIn_store_no() {
		return this.in_store_no;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(Long value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getCreate_emp() {
		return this.create_emp;
	}
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setAudit_emp(Long value) {
		this.audit_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getAudit_emp() {
		return this.audit_emp;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
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