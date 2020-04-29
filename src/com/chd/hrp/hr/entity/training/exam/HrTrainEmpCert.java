package com.chd.hrp.hr.entity.training.exam;

public class HrTrainEmpCert {

	private Long group_id;
	
	private Long hos_id;
	
	private Long plan_id;
	
	private Long emp_id;
	
	private String emp_code;
	
	private String emp_name;
	
	private String dept_name;
	
	private String kind_name;
	
	private String cert_path;
	
	private String cert_name;
	
	private String cert_file_name;
	
	private Integer is_give;
	
	private String is_give_name;
	
	private String cert_code;
	
	public String getCert_code() {
		return cert_code;
	}
	
	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
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

	public String getCert_path() {
		return cert_path;
	}

	public void setCert_path(String cert_path) {
		this.cert_path = cert_path;
	}

	public String getCert_name() {
		return cert_name;
	}

	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}

	public String getCert_file_name() {
		return cert_file_name;
	}

	public void setCert_file_name(String cert_file_name) {
		this.cert_file_name = cert_file_name;
	}

	public Integer getIs_give() {
		return is_give;
	}

	public void setIs_give(Integer is_give) {
		this.is_give = is_give;
	}

	public String getIs_give_name() {
		return is_give_name;
	}

	public void setIs_give_name(String is_give_name) {
		this.is_give_name = is_give_name;
	}
	
}
