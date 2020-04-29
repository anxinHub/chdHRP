package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AphiWorkitemData implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;

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

	private String copy_code;
	private String acct_year;
	private String acct_month;
	private Long dept_no;

	private Long dept_id;
	private String dept_code;
	private String dept_name;
	private String work_item_code;
	private String work_item_name;
	private double work_amount;
	private double work_standard;
	private double work_money;
	private String error_type;

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getWork_item_name() {
		return work_item_name;
	}

	public void setWork_item_name(String work_item_name) {
		this.work_item_name = work_item_name;
	}

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setAcct_year(String value) {
		this.acct_year = value;
	}

	public String getAcct_year() {
		return this.acct_year;
	}

	public void setAcct_month(String value) {
		this.acct_month = value;
	}

	public String getAcct_month() {
		return this.acct_month;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public void setWork_item_code(String value) {
		this.work_item_code = value;
	}

	public String getWork_item_code() {
		return this.work_item_code;
	}

	public void setWork_amount(double value) {
		this.work_amount = value;
	}

	public double getWork_amount() {
		return this.work_amount;
	}

	public void setWork_money(double value) {
		this.work_money = value;
	}

	public double getWork_money() {
		return this.work_money;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public double getWork_standard() {
		return work_standard;
	}

	public void setWork_standard(double work_standard) {
		this.work_standard = work_standard;
	}
	
}