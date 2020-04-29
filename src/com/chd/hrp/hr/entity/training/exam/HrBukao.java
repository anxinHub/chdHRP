package com.chd.hrp.hr.entity.training.exam;

import java.util.Date;

public class HrBukao {

	private Long group_id;
	
	private Long hos_id;
	
	private Long plan_id;
	
	private Date exam_date;
	
	private String exam_date_str;
	
	private String time_begin;
	
	private String time_end;
	
	private String exam_site;
	
	private String exam_way_code;
	
	private Integer emp_num;

	public String getExam_date_str() {
		return exam_date_str;
	}

	public void setExam_date_str(String exam_date_str) {
		this.exam_date_str = exam_date_str;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Date getExam_date() {
		return exam_date;
	}

	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
	}

	public String getExam_site() {
		return exam_site;
	}

	public void setExam_site(String exam_site) {
		this.exam_site = exam_site;
	}

	public Integer getEmp_num() {
		return emp_num;
	}

	public void setEmp_num(Integer emp_num) {
		this.emp_num = emp_num;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(Long plan_id) {
		this.plan_id = plan_id;
	}

	public String getTime_begin() {
		return time_begin;
	}

	public void setTime_begin(String time_begin) {
		this.time_begin = time_begin;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getExam_way_code() {
		return exam_way_code;
	}

	public void setExam_way_code(String exam_way_code) {
		this.exam_way_code = exam_way_code;
	}
	
}
