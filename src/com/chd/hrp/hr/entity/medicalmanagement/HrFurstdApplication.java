package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

public class HrFurstdApplication implements Serializable {

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
	 * 申请单号
	 */
	private String app_no;
	/**
	 * 申请日期
	 */
	private Date app_date;
	/**
	 * 姓名
	 */
	private Integer emp_id;
	private String emp_name;
	/**
	 * 从事专业
	 */
	private String profession;
	/**
	 * 性别
	 */
	private String sex_code;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 进院时间
	 */
	private String hostime;
	/**
	 * 工龄
	 */
	private Integer workage;
	/**
	 * 政治面貌
	 */
	private String political_code;
	/**
	 * 毕业学校
	 */
	private String graduation_school;
	/**
	 * 英语等级
	 */
	private String english_level;
	/**
	 * 进修开始时间
	 */
	private Date beg_date;
	/**
	 * 进修时长
	 */
	private Integer duration;
	/**
	 * 亚专科定位
	 */
	private String sec_profession;
	/**
	 * 进修医院
	 */
	private String furstd_hos;
	/**
	 * 是否提供住宿
	 */
	private Integer hostel;
	/**
	 * 住宿费用
	 */
	private Integer hostel_charge;
	/**
	 * 审批时间
	 */
	private Date audit_date;
	/**
	 * 审批人
	 */
	private String audit_emp;
	/**
	 * 进修目的
	 */
	private String goal;
	/**
	 * 一月计划
	 */
	private String plan1;
	/**
	 * 三月计划
	 */
	private String plan3;
	/**
	 * 六月计划
	 */
	private String plan6;
	/**
	 * 是否提交
	 */
	private Integer is_commit;
	private String commit_name;

	/**
	 * 导入验证信息
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

	public String getApp_no() {
		return app_no;
	}

	public void setApp_no(String app_no) {
		this.app_no = app_no;
	}

	public Date getApp_date() {
		return app_date;
	}

	public void setApp_date(Date app_date) {
		this.app_date = app_date;
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

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSex_code() {
		return sex_code;
	}

	public void setSex_code(String sex_code) {
		this.sex_code = sex_code;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHostime() {
		return hostime;
	}

	public void setHostime(String hostime) {
		this.hostime = hostime;
	}

	public Integer getWorkage() {
		return workage;
	}

	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	public String getPolitical_code() {
		return political_code;
	}

	public void setPolitical_code(String political_code) {
		this.political_code = political_code;
	}

	public String getGraduation_school() {
		return graduation_school;
	}

	public void setGraduation_school(String graduation_school) {
		this.graduation_school = graduation_school;
	}

	public String getEnglish_level() {
		return english_level;
	}

	public void setEnglish_level(String english_level) {
		this.english_level = english_level;
	}

	public Date getBeg_date() {
		return beg_date;
	}

	public void setBeg_date(Date beg_date) {
		this.beg_date = beg_date;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getSec_profession() {
		return sec_profession;
	}

	public void setSec_profession(String sec_profession) {
		this.sec_profession = sec_profession;
	}

	public String getFurstd_hos() {
		return furstd_hos;
	}

	public void setFurstd_hos(String furstd_hos) {
		this.furstd_hos = furstd_hos;
	}

	public Integer getHostel() {
		return hostel;
	}

	public void setHostel(Integer hostel) {
		this.hostel = hostel;
	}

	public Integer getHostel_charge() {
		return hostel_charge;
	}

	public void setHostel_charge(Integer hostel_charge) {
		this.hostel_charge = hostel_charge;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_emp() {
		return audit_emp;
	}

	public void setAudit_emp(String audit_emp) {
		this.audit_emp = audit_emp;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getPlan1() {
		return plan1;
	}

	public void setPlan1(String plan1) {
		this.plan1 = plan1;
	}

	public String getPlan3() {
		return plan3;
	}

	public void setPlan3(String plan3) {
		this.plan3 = plan3;
	}

	public String getPlan6() {
		return plan6;
	}

	public void setPlan6(String plan6) {
		this.plan6 = plan6;
	}

	public Integer getIs_commit() {
		return is_commit;
	}

	public void setIs_commit(Integer is_commit) {
		this.is_commit = is_commit;
	}

	public String getCommit_name() {
		return commit_name;
	}

	public void setCommit_name(String commit_name) {
		this.commit_name = commit_name;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}
