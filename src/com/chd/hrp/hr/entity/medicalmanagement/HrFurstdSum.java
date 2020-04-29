/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description: 进修总结
 * @Table: HR_FURSTD_SUM
 * @Author: ade
 * @email: ade@e-tonggroup.com
 * @Version: 1.0
 */
public class HrFurstdSum implements Serializable {
	private static final long serialVersionUID = 1L;

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
	private Date sum_date;

	/**
	 * 姓名
	 */
	private Integer emp_id;
	private String emp_name;

	/**
	 * 进修医院
	 */
	private String furstd_hos;

	/**
	 * 进修导师
	 */
	private String teacher;

	/**
	 * 导师电话
	 */
	private String tel;

	/**
	 * 进修总结
	 */
	private String summary;

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

	public Date getSum_date() {
		return sum_date;
	}

	public void setSum_date(Date sum_date) {
		this.sum_date = sum_date;
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

	public String getFurstd_hos() {
		return furstd_hos;
	}

	public void setFurstd_hos(String furstd_hos) {
		this.furstd_hos = furstd_hos;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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
