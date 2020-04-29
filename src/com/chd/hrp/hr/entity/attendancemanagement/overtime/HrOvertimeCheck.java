package com.chd.hrp.hr.entity.attendancemanagement.overtime;

import java.io.Serializable;
import java.util.Date;

/**
 * 加班项目设置
 * 
 * @author Administrator
 *
 */
public class HrOvertimeCheck implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	   // 集团ID
		private Integer group_id;
		// 医院ID
		private Integer hos_id;
		// 加班编码
		private String attend_overtime_code;
		//审核人
		private Integer attend_overtime_reviewer;
		//审核日期
		private Date attend_overtime_reviewdate;
		// 错误信息
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
		public String getAttend_overtime_code() {
			return attend_overtime_code;
		}
		public void setAttend_overtime_code(String attend_overtime_code) {
			this.attend_overtime_code = attend_overtime_code;
		}
		public Integer getAttend_overtime_reviewer() {
			return attend_overtime_reviewer;
		}
		public void setAttend_overtime_reviewer(Integer attend_overtime_reviewer) {
			this.attend_overtime_reviewer = attend_overtime_reviewer;
		}
		public Date getAttend_overtime_reviewdate() {
			return attend_overtime_reviewdate;
		}
		public void setAttend_overtime_reviewdate(Date attend_overtime_reviewdate) {
			this.attend_overtime_reviewdate = attend_overtime_reviewdate;
		}
		public String getError_type() {
			return error_type;
		}
		public void setError_type(String error_type) {
			this.error_type = error_type;
		}
		
		
		
		
}
