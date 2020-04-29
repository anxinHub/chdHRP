package com.chd.hrp.hr.entity.salarymanagement.wagePlanManage;

import java.util.List;

/**
 * 薪资方案
 * @author yang
 *
 */
public class HrWagePlan {

	private Integer group_id;
	private Integer hos_id;
	
	private String plan_code;
	private String plan_name;
	
	private String start_date;
	private Integer state;
	private String state_cn;
	private String note;
	
	private List<HrWagePlanKind> empKindList;
	
	public HrWagePlan(){}

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

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getState_cn() {
		return state_cn;
	}

	public void setState_cn(String state_cn) {
		this.state_cn = state_cn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<HrWagePlanKind> getEmpKindList() {
		return empKindList;
	}

	public void setEmpKindList(List<HrWagePlanKind> empKindList) {
		this.empKindList = empKindList;
	}
}
