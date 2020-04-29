/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.entity.prepay;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description: 预付款主表
 * @Table: ASS_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class AssPrePayMain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4643892010318992735L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String pay_no;// 付款单号
	private Date pay_date;// 付款日期
	private Long ven_id;// 供应商
	private Long ven_no;
	private String ven_code;
	private String ven_name;
	private Double pay_money;// 付款金额
	private Integer state;
	private String state_name;
	private Long create_emp;// 制单人
	private String create_emp_name;
	private Date create_date;
	private Long audit_emp;// 审核人
	private String audit_emp_name;
	private Date audit_date;
	private String note;
	private Long operator_emp;
	private String operator_emp_name;
	
	
	

	public Long getOperator_emp() {
		return operator_emp;
	}

	public void setOperator_emp(Long operator_emp) {
		this.operator_emp = operator_emp;
	}

	public String getOperator_emp_name() {
		return operator_emp_name;
	}

	public void setOperator_emp_name(String operator_emp_name) {
		this.operator_emp_name = operator_emp_name;
	}

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getPay_no() {
		return pay_no;
	}

	public void setPay_no(String pay_no) {
		this.pay_no = pay_no;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Long getVen_id() {
		return ven_id;
	}

	public void setVen_id(Long ven_id) {
		this.ven_id = ven_id;
	}

	public Long getVen_no() {
		return ven_no;
	}

	public void setVen_no(Long ven_no) {
		this.ven_no = ven_no;
	}

	public String getVen_code() {
		return ven_code;
	}

	public void setVen_code(String ven_code) {
		this.ven_code = ven_code;
	}

	public String getVen_name() {
		return ven_name;
	}

	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public Long getCreate_emp() {
		return create_emp;
	}

	public void setCreate_emp(Long create_emp) {
		this.create_emp = create_emp;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Long getAudit_emp() {
		return audit_emp;
	}

	public void setAudit_emp(Long audit_emp) {
		this.audit_emp = audit_emp;
	}

	public String getAudit_emp_name() {
		return audit_emp_name;
	}

	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
