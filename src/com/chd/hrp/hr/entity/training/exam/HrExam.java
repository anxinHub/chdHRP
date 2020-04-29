package com.chd.hrp.hr.entity.training.exam;

import java.util.Date;

public class HrExam {

	private Long group_id;
	
	private Long hos_id;
	
	private Long plan_id;
	
	private Date train_date;
	
	private String train_date_str;
	
	private String time_begin;
	
	private String time_end;
	
	private String train_site;
	
	private String exam_way_code;
	
	private String note;

	public String getTrain_date_str() {
		return train_date_str;
	}

	public void setTrain_date_str(String train_date_str) {
		this.train_date_str = train_date_str;
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

	public Long getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(Long plan_id) {
		this.plan_id = plan_id;
	}

	public Date getTrain_date() {
		return train_date;
	}

	public void setTrain_date(Date train_date) {
		this.train_date = train_date;
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

	public String getTrain_site() {
		return train_site;
	}

	public void setTrain_site(String train_site) {
		this.train_site = train_site;
	}

	public String getExam_way_code() {
		return exam_way_code;
	}

	public void setExam_way_code(String exam_way_code) {
		this.exam_way_code = exam_way_code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
