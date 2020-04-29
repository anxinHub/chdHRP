package com.chd.hrp.hr.entity.transfer;

import java.io.Serializable;
import java.util.Date;


/**
 * 部门调岗
 * @author Administrator
 *
 */
public class HrDeptTransfer implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String group_id;
	
	/**
	 * 
	 */
	private String hos_id;
	/**
	 * 调动编号
	 */
	private String adjust_code;
	/**
	 * 职工id
	 */
	private Integer emp_id;
	private String emp_name;
	private String emp_code;
	private String photo;
	private String kind_name;
	/**
	 * 调动日期
	 */
	private Date adjust_date;
	/**
	 * 调动原因
	 */
	private String adjust_reason;
	/**
	 * 调整前部门ID
	 */
	private Integer bef_dept_id;
	private String bef_dept_name;
	/**
	 *调整前部门变更ID
	 */
	private Integer bef_dept_no;
	/**
	 * 调整后部门ID
	 */
	private Integer aft_dept_id;
	private String aft_dept_name;
	/**
	 * 调整后部门变更ID
	 */
	private Integer aft_dept_no;
	/**
	 * 状态：0:新建，1:审核
	 */
	private Integer adjust_state;
    private String adjust_state_name;
	/**
	 * 制单人
	 */
	private Integer maker;
	private String maker_name;
	/**
	 * 制单日期
	 */
	private Date make_date;
	/**
	 * 审核人
	 */
	private Integer checker;
	private String checker_name;
	/**
	 * 审核日期
	 */
	private Date check_date;
	 /**
	  * 错误信息
      */
	private String error_type;
	
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getHos_id() {
		return hos_id;
	}
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}
	public String getAdjust_code() {
		return adjust_code;
	}
	public void setAdjust_code(String adjust_code) {
		this.adjust_code = adjust_code;
	}
	public Integer getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Date getAdjust_date() {
		return adjust_date;
	}
	public void setAdjust_date(Date adjust_date) {
		this.adjust_date = adjust_date;
	}
	public String getAdjust_reason() {
		return adjust_reason;
	}
	public void setAdjust_reason(String adjust_reason) {
		this.adjust_reason = adjust_reason;
	}
	public Integer getBef_dept_id() {
		return bef_dept_id;
	}
	public void setBef_dept_id(Integer bef_dept_id) {
		this.bef_dept_id = bef_dept_id;
	}
	public String getBef_dept_name() {
		return bef_dept_name;
	}
	public void setBef_dept_name(String bef_dept_name) {
		this.bef_dept_name = bef_dept_name;
	}
	public Integer getBef_dept_no() {
		return bef_dept_no;
	}
	public void setBef_dept_no(Integer bef_dept_no) {
		this.bef_dept_no = bef_dept_no;
	}
	public Integer getAft_dept_id() {
		return aft_dept_id;
	}
	public void setAft_dept_id(Integer aft_dept_id) {
		this.aft_dept_id = aft_dept_id;
	}
	public String getAft_dept_name() {
		return aft_dept_name;
	}
	public void setAft_dept_name(String aft_dept_name) {
		this.aft_dept_name = aft_dept_name;
	}
	public Integer getAft_dept_no() {
		return aft_dept_no;
	}
	public void setAft_dept_no(Integer aft_dept_no) {
		this.aft_dept_no = aft_dept_no;
	}
	public Integer getAdjust_state() {
		return adjust_state;
	}
	public void setAdjust_state(Integer adjust_state) {
		this.adjust_state = adjust_state;
	}
	public String getAdjust_state_name() {
		return adjust_state_name;
	}
	public void setAdjust_state_name(String adjust_state_name) {
		this.adjust_state_name = adjust_state_name;
	}
	public Integer getMaker() {
		return maker;
	}
	public void setMaker(Integer maker) {
		this.maker = maker;
	}
	public String getMaker_name() {
		return maker_name;
	}
	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}
	public Date getMake_date() {
		return make_date;
	}
	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}
	public Integer getChecker() {
		return checker;
	}
	public void setChecker(Integer checker) {
		this.checker = checker;
	}
	public String getChecker_name() {
		return checker_name;
	}
	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}
	public Date getCheck_date() {
		return check_date;
	}
	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	
	
}
