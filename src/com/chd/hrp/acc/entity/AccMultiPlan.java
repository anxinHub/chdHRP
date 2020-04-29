package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccMultiPlan implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long group_id;
	
	private long hos_id;
	
	private String copy_code;
	
	private String plan_code;
	
	private String plan_name;
	
	private String subj_code;
	
	private String subj_name;
	
	private Integer analy_type;
	
	private String note;

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public long getHos_id() {
		return hos_id;
	}

	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public Integer getAnaly_type() {
		return analy_type;
	}

	public void setAnaly_type(Integer analy_type) {
		this.analy_type = analy_type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSubj_name() {
		return subj_name;
	}

	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	
	

}
