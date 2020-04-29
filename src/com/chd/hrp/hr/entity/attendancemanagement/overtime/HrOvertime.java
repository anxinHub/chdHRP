package com.chd.hrp.hr.entity.attendancemanagement.overtime;

import java.io.Serializable;
import java.util.Date;

/**
 * 期初加班登记
 * @author Administrator
 *
 */
public class HrOvertime implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
	private Integer group_id;
	// 医院ID
	private Integer hos_id;
	// 加班编码
	private String attend_overtime_code;
	//职工编码
	private Integer emp_id;
	private String emp_name;
	private String bdept_id;
	private String bdept_name;
	private String cdept_id;
	private String cdept_name;
	private String photo;
	//加班类型
	private String attend_code;
	private String kind_name;
	//加班开始时间
	private Date attend_overtime_begdate;
	private Date attend_overtime_begdate_hour;
	//加班结束时间
	private Date attend_overtime_enddate;
	private Date attend_overtime_enddate_hour;
	//加班小时数
	private Float attend_overtime_hour;
	//提交人
	private Integer attend_overtime_loginer;
	private String loginer_name;
	//提交日期
	private Date attend_overtime_reg_operdate;
	//核算天数
	private Float attend_accchadays;
	//状态
	private Integer attend_overtime_state;
	private String  state_name;
	//审核人
	private Integer attend_overtime_reviewer;
	private String reviewer_name;
	//审核日期
	private Date attend_overtime_reviewdate;
	// 错误信息
	private String error_type;
	private String note;
	private Date create_date;
	
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
	
	public String getBdept_id() {
		return bdept_id;
	}
	public void setBdept_id(String bdept_id) {
		this.bdept_id = bdept_id;
	}
	public String getBdept_name() {
		return bdept_name;
	}
	public void setBdept_name(String bdept_name) {
		this.bdept_name = bdept_name;
	}
	public String getCdept_id() {
		return cdept_id;
	}
	public void setCdept_id(String cdept_id) {
		this.cdept_id = cdept_id;
	}
	public String getCdept_name() {
		return cdept_name;
	}
	public void setCdept_name(String cdept_name) {
		this.cdept_name = cdept_name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public Date getAttend_overtime_begdate() {
		return attend_overtime_begdate;
	}
	public String getAttend_code() {
		return attend_code;
	}
	public void setAttend_code(String attend_code) {
		this.attend_code = attend_code;
	}
	public void setAttend_overtime_begdate(Date attend_overtime_begdate) {
		this.attend_overtime_begdate = attend_overtime_begdate;
	}
	public Date getAttend_overtime_enddate() {
		return attend_overtime_enddate;
	}
	public void setAttend_overtime_enddate(Date attend_overtime_enddate) {
		this.attend_overtime_enddate = attend_overtime_enddate;
	}
	public Float getAttend_overtime_hour() {
		return attend_overtime_hour;
	}
	public void setAttend_overtime_hour(Float attend_overtime_hour) {
		this.attend_overtime_hour = attend_overtime_hour;
	}
	public Integer getAttend_overtime_loginer() {
		return attend_overtime_loginer;
	}
	public void setAttend_overtime_loginer(Integer attend_overtime_loginer) {
		this.attend_overtime_loginer = attend_overtime_loginer;
	}
	
	
	public String getLoginer_name() {
		return loginer_name;
	}
	public void setLoginer_name(String loginer_name) {
		this.loginer_name = loginer_name;
	}
	public Date getAttend_overtime_reg_operdate() {
		return attend_overtime_reg_operdate;
	}
	public void setAttend_overtime_reg_operdate(Date attend_overtime_reg_operdate) {
		this.attend_overtime_reg_operdate = attend_overtime_reg_operdate;
	}
	public Float getAttend_accchadays() {
		return attend_accchadays;
	}
	public void setAttend_accchadays(Float attend_accchadays) {
		this.attend_accchadays = attend_accchadays;
	}
	public Integer getAttend_overtime_state() {
		return attend_overtime_state;
	}
	public void setAttend_overtime_state(Integer attend_overtime_state) {
		this.attend_overtime_state = attend_overtime_state;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	
	public Integer getAttend_overtime_reviewer() {
		return attend_overtime_reviewer;
	}
	public void setAttend_overtime_reviewer(Integer attend_overtime_reviewer) {
		this.attend_overtime_reviewer = attend_overtime_reviewer;
	}
	public String getReviewer_name() {
		return reviewer_name;
	}
	public void setReviewer_name(String reviewer_name) {
		this.reviewer_name = reviewer_name;
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
	public Date getAttend_overtime_begdate_hour() {
		return attend_overtime_begdate_hour;
	}
	public void setAttend_overtime_begdate_hour(Date attend_overtime_begdate_hour) {
		this.attend_overtime_begdate_hour = attend_overtime_begdate_hour;
	}
	public Date getAttend_overtime_enddate_hour() {
		return attend_overtime_enddate_hour;
	}
	public void setAttend_overtime_enddate_hour(Date attend_overtime_enddate_hour) {
		this.attend_overtime_enddate_hour = attend_overtime_enddate_hour;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
}
