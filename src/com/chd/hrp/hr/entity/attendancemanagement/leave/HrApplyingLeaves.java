package com.chd.hrp.hr.entity.attendancemanagement.leave;

import java.io.Serializable;
import java.util.Date;

/**
 * 职工休假申请
 * 
 * @author Administrator
 *
 */
public class HrApplyingLeaves implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 集团ID
	 */
	private Integer group_id;
	/**
	 * 医院ID
	 */
	private Integer hos_id;
	/**
	 * 休假申请编码
	 */
	private String attend_xjapply_code;
	/**
	 * 考勤项目编码
	 */
	private String attend_code;
	private String attend_name;
	/**
	 * 登记日期
	 */
	private Date attend_xjreg_date;
	/**
	 * 职工编码
	 */
	private Integer emp_id;
	private String emp_name;
	private String dept_name;
	private String duty_name;
	private String birthday;
	private String sex_name;
	private String worktime;
	private String workage;
	private String birthplace;
	private String photo;
	/**
	 * 休假年份
	 */
	private String attend_year;
	/**
	 * 休假开始时间
	 */
	private Date attend_xjbegdate;
	private Integer attend_xjbegdate_ampm;
	/**
	 * 休假结束时间
	 */
	private Date attend_xjenddate;
	private Integer attend_xjenddate_ampm;
	/**
	 * 休假天数
	 */
	private Double attend_xjdays;
	private String attend_ed;
	private String attend_ed_is;
	private String xjdays;
	private String residue_days;
	/**
	 * 状态
	 */
	private Integer attend_xjstate;
	private String attend_xjstate_name;
	/**
	 * 提交人
	 */
	private Integer attend_xjloginer;
	private String attend_xjloginer_name;
	/**
	 * 操作日期
	 */
	private Date attend_xjreg_operdate;
	/**
	 * 审核人
	 */
	private Integer attend_xjreviewer;
	private String attend_xjreviewer_name;
	/**
	 * 审核日期
	 */
	private Date attend_xjreg_reviewdate;
	/**
	 * 休假原因
	 */
	private String attend_xj_reason;
	/**
	 * 核定状态
	 */
	private Integer attend_xjcheck_state;
	private String attend_xjcheck_state_name;

	/**
	 * 核定人
	 */
	private Integer attend_xjchecker;
	private String attend_xjchecker_name;
	/**
	 * 核定日期
	 */
	private Date attend_xjcheckdate;
	/**
	 * 是否补登
	 */
	private Integer attend_xj_add;
	private String attend_xj_add_name;
	/**
	 * 错误信息
	 */
	private String error_type;

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

	public String getAttend_xjapply_code() {
		return attend_xjapply_code;
	}

	public void setAttend_xjapply_code(String attend_xjapply_code) {
		this.attend_xjapply_code = attend_xjapply_code;
	}

	public String getAttend_code() {
		return attend_code;
	}

	public void setAttend_code(String attend_code) {
		this.attend_code = attend_code;
	}

	public Date getAttend_xjreg_date() {
		return attend_xjreg_date;
	}

	public void setAttend_xjreg_date(Date attend_xjreg_date) {
		this.attend_xjreg_date = attend_xjreg_date;
	}

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getAttend_year() {
		return attend_year;
	}

	public void setAttend_xjyear(String attend_year) {
		this.attend_year = attend_year;
	}

	public Date getAttend_xjbegdate() {
		return attend_xjbegdate;
	}

	public void setAttend_xjbegdate(Date attend_xjbegdate) {
		this.attend_xjbegdate = attend_xjbegdate;
	}

	public Date getAttend_xjenddate() {
		return attend_xjenddate;
	}

	public void setAttend_xjenddate(Date attend_xjenddate) {
		this.attend_xjenddate = attend_xjenddate;
	}

	public Double getAttend_xjdays() {
		return attend_xjdays;
	}

	public void setAttend_xjdays(Double attend_xjdays) {
		this.attend_xjdays = attend_xjdays;
	}

	public Integer getAttend_xjstate() {
		return attend_xjstate;
	}

	public void setAttend_xjstate(Integer attend_xjstate) {
		this.attend_xjstate = attend_xjstate;
	}

	public Integer getAttend_xjloginer() {
		return attend_xjloginer;
	}

	public void setAttend_xjloginer(Integer attend_xjloginer) {
		this.attend_xjloginer = attend_xjloginer;
	}

	public Date getAttend_xjreg_operdate() {
		return attend_xjreg_operdate;
	}

	public void setAttend_xjreg_operdate(Date attend_xjreg_operdate) {
		this.attend_xjreg_operdate = attend_xjreg_operdate;
	}

	public Integer getAttend_xjreviewer() {
		return attend_xjreviewer;
	}

	public void setAttend_xjreviewer(Integer attend_xjreviewer) {
		this.attend_xjreviewer = attend_xjreviewer;
	}

	public Date getAttend_xjreg_reviewdate() {
		return attend_xjreg_reviewdate;
	}

	public void setAttend_xjreg_reviewdate(Date attend_xjreg_reviewdate) {
		this.attend_xjreg_reviewdate = attend_xjreg_reviewdate;
	}

	public String getAttend_xj_reason() {
		return attend_xj_reason;
	}

	public void setAttend_xj_reason(String attend_xj_reason) {
		this.attend_xj_reason = attend_xj_reason;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getAttend_xjloginer_name() {
		return attend_xjloginer_name;
	}

	public void setAttend_xjloginer_name(String attend_xjloginer_name) {
		this.attend_xjloginer_name = attend_xjloginer_name;
	}

	public String getAttend_xjreviewer_name() {
		return attend_xjreviewer_name;
	}

	public void setAttend_xjreviewer_name(String attend_xjreviewer_name) {
		this.attend_xjreviewer_name = attend_xjreviewer_name;
	}

	public String getAttend_xjstate_name() {
		return attend_xjstate_name;
	}

	public void setAttend_xjstate_name(String attend_xjstate_name) {
		this.attend_xjstate_name = attend_xjstate_name;
	}

	public Integer getAttend_xjcheck_state() {
		return attend_xjcheck_state;
	}

	public void setAttend_xjcheck_state(Integer attend_xjcheck_state) {
		this.attend_xjcheck_state = attend_xjcheck_state;
	}

	public Integer getAttend_xjchecker() {
		return attend_xjchecker;
	}

	public void setAttend_xjchecker(Integer attend_xjchecker) {
		this.attend_xjchecker = attend_xjchecker;
	}

	public String getAttend_xjchecker_name() {
		return attend_xjchecker_name;
	}

	public void setAttend_xjchecker_name(String attend_xjchecker_name) {
		this.attend_xjchecker_name = attend_xjchecker_name;
	}

	public Date getAttend_xjcheckdate() {
		return attend_xjcheckdate;
	}

	public void setAttend_xjcheckdate(Date attend_xjcheckdate) {
		this.attend_xjcheckdate = attend_xjcheckdate;
	}

	public String getAttend_xjcheck_state_name() {
		return attend_xjcheck_state_name;
	}

	public void setAttend_xjcheck_state_name(String attend_xjcheck_state_name) {
		this.attend_xjcheck_state_name = attend_xjcheck_state_name;
	}

	public String getAttend_name() {
		return attend_name;
	}

	public void setAttend_name(String attend_name) {
		this.attend_name = attend_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex_name() {
		return sex_name;
	}

	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}

	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public String getWorkage() {
		return workage;
	}

	public void setWorkage(String workage) {
		this.workage = workage;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAttend_ed() {
		return attend_ed;
	}

	public void setAttend_ed(String attend_ed) {
		this.attend_ed = attend_ed;
	}

	public String getXjdays() {
		return xjdays;
	}

	public void setXjdays(String xjdays) {
		this.xjdays = xjdays;
	}

	public String getResidue_days() {
		return residue_days;
	}

	public void setResidue_days(String residue_days) {
		this.residue_days = residue_days;
	}

	public Integer getAttend_xjbegdate_ampm() {
		return attend_xjbegdate_ampm;
	}

	public void setAttend_xjbegdate_ampm(Integer attend_xjbegdate_ampm) {
		this.attend_xjbegdate_ampm = attend_xjbegdate_ampm;
	}

	public Integer getAttend_xjenddate_ampm() {
		return attend_xjenddate_ampm;
	}

	public void setAttend_xjenddate_ampm(Integer attend_xjenddate_ampm) {
		this.attend_xjenddate_ampm = attend_xjenddate_ampm;
	}
	public String getAttend_ed_is() {
		return attend_ed_is;
	}
	public void setAttend_ed_is(String attend_ed_is) {
		this.attend_ed_is = attend_ed_is;
	}
}
