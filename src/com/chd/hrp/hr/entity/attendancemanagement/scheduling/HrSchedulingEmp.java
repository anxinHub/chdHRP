package com.chd.hrp.hr.entity.attendancemanagement.scheduling;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

//排班处理-排班人员和排班表设置
public class HrSchedulingEmp implements Serializable {
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
	 * 排序号
	 */
	private Integer sort_code;
	
	/**
	 * 区域编码
	 */
	private Integer emp_id;
	private String emp_name;
	private Integer dept_id;
	private String  dept_code;
	private String dept_name;
	private Integer level_code;
	private String level_name;
	
	private Integer db_gz;
	private Integer is_cq;
	/**
	 * 排班日期
	 */
	private Date pb_date;
	
	/**
	 * 班次编码
	 */
	private String attend_classcode;
	private String attend_classsname;
	/**
	 * 出勤科室
	 */
	private Integer dept;
	private String each_dept_name;
	private String [] params;
	private Map<Object, String> hrSchedulingEmp;
	
	private List<Map<Object,String>> param;
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
	
	
	public Integer getSort_code() {
		return sort_code;
	}
	public void setSort_code(Integer sort_code) {
		this.sort_code = sort_code;
	}
	public Integer getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public Date getPb_date() {
		return pb_date;
	}
	public void setPb_date(Date pb_date) {
		this.pb_date = pb_date;
	}
	public String getAttend_classcode() {
		return attend_classcode;
	}
	public void setAttend_classcode(String attend_classcode) {
		this.attend_classcode = attend_classcode;
	}

	public Integer getDept() {
		return dept;
	}
	public void setDept(Integer dept) {
		this.dept = dept;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	

	public String getAttend_classsname() {
		return attend_classsname;
	}
	public void setAttend_classsname(String attend_classsname) {
		this.attend_classsname = attend_classsname;
	}
	public String getEach_dept_name() {
		return each_dept_name;
	}
	public void setEach_dept_name(String each_dept_name) {
		this.each_dept_name = each_dept_name;
	}
	public String[] getParams() {
		return params;
	}
	public void setParams(String[] params) {
		this.params = params;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public Map<Object, String> getHrSchedulingEmp() {
		return hrSchedulingEmp;
	}
	public void setHrSchedulingEmp(Map<Object, String> hrSchedulingEmp) {
		this.hrSchedulingEmp = hrSchedulingEmp;
	}
	public List<Map<Object, String>> getParam() {
		return param;
	}
	public void setParam(List<Map<Object, String>> param) {
		this.param = param;
	}

	public Integer getLevel_code() {
		return level_code;
	}
	public void setLevel_code(Integer level_code) {
		this.level_code = level_code;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public Integer getDb_gz() {
		return db_gz;
	}
	public void setDb_gz(Integer db_gz) {
		this.db_gz = db_gz;
	}
	public Integer getIs_cq() {
		return is_cq;
	}
	public void setIs_cq(Integer is_cq) {
		this.is_cq = is_cq;
	}


	
	
}
