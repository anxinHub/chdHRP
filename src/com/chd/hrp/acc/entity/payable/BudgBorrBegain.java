package com.chd.hrp.acc.entity.payable;

import java.io.Serializable;
import java.util.Date;

public class BudgBorrBegain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String begin_code;
	private Integer dept_id;
	private Integer dept_no;
	private Integer proj_id;
	private Integer proj_no;
	private Integer emp_id;
	private Integer emp_no;
	private String dept_code;
	private String dept_name;
	private String proj_code;
	private String proj_name;
	private String emp_code;
	private String emp_name;
	private Double remain_amount;
	private String remark;
	private Integer maker;
	private String maker_name;
	private Date make_date; 
	private Integer checker;
	private String checker_name;
	private Date check_date;
	private String state;
	
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
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getMaker_name() {
		return maker_name;
	}
	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}
	public String getChecker_name() {
		return checker_name;
	}
	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getHos_id() {
		return hos_id;
	}
	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	public Integer getDept_no() {
		return dept_no;
	}
	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}
	public Integer getProj_no() {
		return proj_no;
	}
	public void setProj_no(Integer proj_no) {
		this.proj_no = proj_no;
	}
	public Double getRemain_amount() {
		return remain_amount;
	}
	public void setRemain_amount(Double remain_amount) {
		this.remain_amount = remain_amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getChecker() {
		return checker;
	}
	public void setChecker(Integer checker) {
		this.checker = checker;
	}
	public Date getCheck_date() {
		return check_date;
	}
	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBegin_code() {
		return begin_code;
	}
	public void setBegin_code(String begin_code) {
		this.begin_code = begin_code;
	}
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	public Integer getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}
	public Integer getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(Integer emp_no) {
		this.emp_no = emp_no;
	}
	public Integer getMaker() {
		return maker;
	}
	public void setMaker(Integer maker) {
		this.maker = maker;
	}
	public Date getMake_date() {
		return make_date;
	}
	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}
	
}
