package com.chd.hrp.hr.entity.training.exam;

public class HrExamResult {

	private Long group_id;
	private Long hos_id;
	private Long plan_id;
	private Long emp_id;
	private String emp_code;
	private String emp_name;
	private String kind_name;
	private String dept_name;
	private Double score;
	private Integer is_pass;
	private String is_pass_name;
	private String paper_path;
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
	public Long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
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
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getIs_pass() {
		return is_pass;
	}
	public void setIs_pass(Integer is_pass) {
		this.is_pass = is_pass;
	}
	public String getPaper_path() {
		return paper_path;
	}
	public void setPaper_path(String paper_path) {
		this.paper_path = paper_path;
	}
	public String getIs_pass_name() {
		return is_pass_name;
	}
	public void setIs_pass_name(String is_pass_name) {
		this.is_pass_name = is_pass_name;
	}
	
	
}
