package com.chd.hrp.hr.entity.attendancemanagement.attendresult;

import java.io.Serializable;
import java.util.Date;

public class HrAttendResult implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
   private Integer group_id;
   
   //医院ID
   private Integer hos_id;
   
   //考勤周期
   private String attend_cycledate;
   
   //部门ID
   private Integer dept_id;
   private String dept_name;
   
   //员工ID
   private Integer emp_id;
   private String emp_name;
   
   //结果数据发生日期
   private Date attend_result_date;
   
   //结果数据类型
   private Integer attend_result_type;
   private String result_type_name;
   
   private String attend_code;
   private String attend_name;
   
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
	public String getAttend_cycledate() {
		return attend_cycledate;
	}
	public void setAttend_cycledate(String attend_cycledate) {
		this.attend_cycledate = attend_cycledate;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	public Date getAttend_result_date() {
		return attend_result_date;
	}
	public void setAttend_result_date(Date attend_result_date) {
		this.attend_result_date = attend_result_date;
	}
	
	public String getAttend_code() {
		return attend_code;
	}
	public void setAttend_code(String attend_code) {
		this.attend_code = attend_code;
	}
	public String getAttend_name() {
		return attend_name;
	}
	public void setAttend_name(String attend_name) {
		this.attend_name = attend_name;
	}
   
   
   
   
}
