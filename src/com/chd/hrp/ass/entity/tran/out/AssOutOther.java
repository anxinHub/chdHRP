/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.tran.out;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050902 资产领用表_其他固定资产
 * @Table:
 * ASS_OUT_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssOutOther implements Serializable {

	
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
	private String out_no;
	
	/**
	 * 单据类型  01: 科室领用  04 : 科室退库
	 */
	private String bill_type;
	
	private String bill_type_name;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 仓库变更NO
	 */
	private Long store_no;
	
	private String store_code;
	
	private String store_name;
	
	/**
	 * 科室ID
	 */
	private String dept_id;
	
	/**
	 * 科室变更NO
	 */
	private String dept_no;
	
	private String dept_code;
	
	private String dept_name;
	
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
	
	private String is_import;
	
	private Long purc_emp;
	private String purc_emp_name;
	
	
	
	
	
	public Long getPurc_emp() {
		return purc_emp;
	}

	public void setPurc_emp(Long purc_emp) {
		this.purc_emp = purc_emp;
	}

	public String getPurc_emp_name() {
		return purc_emp_name;
	}

	public void setPurc_emp_name(String purc_emp_name) {
		this.purc_emp_name = purc_emp_name;
	}
	
	
	public String getIs_import() {
		return is_import;
	}

	public void setIs_import(String is_import) {
		this.is_import = is_import;
	}

	public String getBill_type_name() {
		return bill_type_name;
	}
	
	public void setBill_type_name(String bill_type_name) {
		this.bill_type_name = bill_type_name;
	}
	
	public String getStore_code() {
		return store_code;
	}
	
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	
	public String getStore_name() {
		return store_name;
	}
	
	public void setStore_name(String store_name) {
		this.store_name = store_name;
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
	public void setOut_no(String value) {
		this.out_no = value;
	}
	
	/**
	* 获取 资产转移单号
	* @return String
	*/
	public String getOut_no() {
		return this.out_no;
	}
	/**
	* 设置 单据类型  01: 科室领用  04 : 科室退库
	* @param value 
	*/
	public void setBill_type(String value) {
		this.bill_type = value;
	}
	
	/**
	* 获取 单据类型  01: 科室领用  04 : 科室退库
	* @return String
	*/
	public String getBill_type() {
		return this.bill_type;
	}
	/**
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 仓库变更NO
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 仓库变更NO
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 科室ID
	* @param value 
	*/
	public void setDept_id(String value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室ID
	* @return String
	*/
	public String getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 科室变更NO
	* @param value 
	*/
	public void setDept_no(String value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室变更NO
	* @return String
	*/
	public String getDept_no() {
		return this.dept_no;
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