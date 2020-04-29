/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.bill.back;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 红冲发票主表
 * @Table:
 * ASS_BACK_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssBackBillMain implements Serializable {

	
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
	 * 发票流水号
	 */
	private String bill_no;
	
	/**
	 * 发票号
	 */
	private String invoice_no;
	
	/**
	 * 开票日期
	 */
	private Date bill_date;
	
	/**
	 * 供应商编码
	 */
	private String ven_id;
	
	private String ven_no;
	
	private String ven_code;
	
	private String ven_name;
	
	/**
	 * 合同ID
	 */
	
	private String pact_code;
	
	/**
	 * 发票金额
	 */
	private Double bill_money;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	private String state_name;
	
	/**
	 * 制单人
	 */
	private String create_emp;
	
	private String create_emp_name;
	
	/**
	 * 制单时间
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private String audit_emp;
	
	private String audit_emp_name;
	
	/**
	 * 审核时间
	 */
	private Date audit_date;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
    private Integer store_id;
	
	private Integer store_no;
	
	private String store_code;
	
	private String store_name;
	
	

    public Integer getStore_id() {
		return store_id;
	}

	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}

	public Integer getStore_no() {
		return store_no;
	}

	public void setStore_no(Integer store_no) {
		this.store_no = store_no;
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
	
	
	public String getVen_id() {
		return ven_id;
	}
	
	public void setVen_id(String ven_id) {
		this.ven_id = ven_id;
	}
	
	public String getVen_no() {
		return ven_no;
	}
	
	public void setVen_no(String ven_no) {
		this.ven_no = ven_no;
	}
	
	public String getVen_name() {
		return ven_name;
	}
	
	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}
	
	
	
	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public String getState_name() {
		return state_name;
	}
	
	public void setState_name(String state_name) {
		this.state_name = state_name;
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
	* 设置 发票流水号
	* @param value 
	*/
	public void setBill_no(String value) {
		this.bill_no = value;
	}
	
	/**
	* 获取 发票流水号
	* @return String
	*/
	public String getBill_no() {
		return this.bill_no;
	}
	/**
	* 设置 发票号
	* @param value 
	*/
	public void setInvoice_no(String value) {
		this.invoice_no = value;
	}
	
	/**
	* 获取 发票号
	* @return String
	*/
	public String getInvoice_no() {
		return this.invoice_no;
	}
	/**
	* 设置 开票日期
	* @param value 
	*/
	public void setBill_date(Date value) {
		this.bill_date = value;
	}
	
	/**
	* 获取 开票日期
	* @return Date
	*/
	public Date getBill_date() {
		return this.bill_date;
	}
	/**
	* 设置 供应商编码
	* @param value 
	*/
	public void setVen_code(String value) {
		this.ven_code = value;
	}
	
	/**
	* 获取 供应商编码
	* @return String
	*/
	public String getVen_code() {
		return this.ven_code;
	}

	/**
	* 设置 发票金额
	* @param value 
	*/
	public void setBill_money(Double value) {
		this.bill_money = value;
	}
	
	/**
	* 获取 发票金额
	* @return Double
	*/
	public Double getBill_money() {
		return this.bill_money;
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
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(String value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return String
	*/
	public String getCreate_emp() {
		return this.create_emp;
	}
	/**
	* 设置 制单时间
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单时间
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setAudit_emp(String value) {
		this.audit_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getAudit_emp() {
		return this.audit_emp;
	}
	/**
	* 设置 审核时间
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核时间
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
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