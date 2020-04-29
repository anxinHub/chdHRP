package com.chd.hrp.hr.entity.attendancemanagement.leave;

import java.io.Serializable;

public class HrXJEDEmp  implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	// 集团ID
		private Integer group_id;
		// 医院ID
		private Integer hos_id;
		//考勤额度编码
		private String attend_ed_code;
		//年份
		private String attend_year;
		//人员
		private String emp_id;
		private String emp_name;
		private Integer dept_id;
		private String dept_no;
		private String dept_name;
		/**
		 * 
		 */
		private String duty_code;
		
		private String duty_name;
	
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

		public String getAttend_ed_code() {
			return attend_ed_code;
		}

		public void setAttend_ed_code(String attend_ed_code) {
			this.attend_ed_code = attend_ed_code;
		}

		public String getAttend_year() {
			return attend_year;
		}

		public void setAttend_year(String attend_year) {
			this.attend_year = attend_year;
		}

		public String getEmp_id() {
			return emp_id;
		}

		public void setEmp_id(String emp_id) {
			this.emp_id = emp_id;
		}

		public String getEmp_name() {
			return emp_name;
		}

		public void setEmp_name(String emp_name) {
			this.emp_name = emp_name;
		}

		public String getError_type() {
			return error_type;
		}

		public void setError_type(String error_type) {
			this.error_type = error_type;
		}

		public Integer getDept_id() {
			return dept_id;
		}

		public void setDept_id(Integer dept_id) {
			this.dept_id = dept_id;
		}

		public String getDept_no() {
			return dept_no;
		}

		public void setDept_no(String dept_no) {
			this.dept_no = dept_no;
		}

		public String getDept_name() {
			return dept_name;
		}

		public void setDept_name(String dept_name) {
			this.dept_name = dept_name;
		}

		public String getDuty_code() {
			return duty_code;
		}

		public void setDuty_code(String duty_code) {
			this.duty_code = duty_code;
		}

		public String getDuty_name() {
			return duty_name;
		}

		public void setDuty_name(String duty_name) {
			this.duty_name = duty_name;
		}
		
		
}
