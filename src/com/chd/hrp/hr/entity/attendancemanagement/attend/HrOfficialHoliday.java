package com.chd.hrp.hr.entity.attendancemanagement.attend;

import java.io.Serializable;
import java.util.Date;

/**
 * 法定节假日设置
 * 
 * @author Administrator
 *
 */
public class HrOfficialHoliday implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
	private Long group_id;
	// 医院ID
	private Long hos_id;

	// 考勤时间
	private Date attend_date;
	// 考勤时间状态
	private String attend_date_state;

	// 错误信息
	private String error_type;

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

	

	public Date getAttend_date() {
		return attend_date;
	}

	public void setAttend_date(Date attend_date) {
		this.attend_date = attend_date;
	}

	public String getAttend_date_state() {
		return attend_date_state;
	}

	public void setAttend_date_state(String attend_date_state) {
		this.attend_date_state = attend_date_state;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}
