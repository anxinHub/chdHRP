package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiDeptCost  implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private Long group_id;

	private Long hos_id;
	
	private String copy_code;
	
	private String acct_year;
	
	private String acct_month;
	
	private Long dept_id;
	
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;
	
	private String cost_item_code;
	
	private String cost_iitem_name;

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

	public String getAcct_year() {
		return acct_year;
	}

	public void setAcct_year(String acct_year) {
		this.acct_year = acct_year;
	}

	public String getAcct_month() {
		return acct_month;
	}

	public void setAcct_month(String acct_month) {
		this.acct_month = acct_month;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
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

	public String getCost_item_code() {
		return cost_item_code;
	}

	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}

	public String getCost_iitem_name() {
		return cost_iitem_name;
	}

	public void setCost_iitem_name(String cost_iitem_name) {
		this.cost_iitem_name = cost_iitem_name;
	}

}
