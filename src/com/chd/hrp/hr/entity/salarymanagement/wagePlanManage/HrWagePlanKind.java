package com.chd.hrp.hr.entity.salarymanagement.wagePlanManage;

public class HrWagePlanKind {
	
	private Integer group_id;
	private Integer hos_id;
	private String plan_code;
	private String emp_kind_code;
	
	public HrWagePlanKind(){}

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

	public String getEmp_kind_code() {
		return emp_kind_code;
	}

	public void setEmp_kind_code(String emp_kind_code) {
		this.emp_kind_code = emp_kind_code;
	}
}
