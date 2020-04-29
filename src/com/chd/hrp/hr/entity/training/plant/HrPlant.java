package com.chd.hrp.hr.entity.training.plant;

import java.io.Serializable;

public class HrPlant implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 培训计划id
	 */
	private Long plan_id;
	
	
	/**
	 * 培训培训类别
	 */
	private String train_type_code;
	private String train_type_name;

	
	/**
	 * 培训部门ID
	 */
	private Integer dept_id;
    private String dept_name;
	
	/**
	 * 培训部门变更
	 */
	private Integer dept_no;

	
	/**
	 * 年度
	 */
	private String year;

	
	/**
	 * 月份
	 */
	private String month;

	
	/**
	 * 培训主题
	 */
	private String train_title;

	
	/**
	 * 培训方式
	 */
	private String train_way_code;
	private String train_way_name;

	
	/**
	 * 培训对象
	 */
	private String train_object;

	
	/**
	 * 是否考核
	 */
	private Integer is_exam;
	private String is_exam_name;

	
	/**
	 * 是否发证
	 */
	private Integer is_cert;
	private String is_cert_name;

	
	/**
	 * 学分
	 */
	private Integer credit_hour;
	
	/**
	 * 是否执行
	 */
	private Integer id_exe;
	private String id_exe_name;
	
	/**
	 * 备注
	 */
   private String  note;

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

	public String getTrain_type_code() {
		return train_type_code;
	}

	public void setTrain_type_code(String train_type_code) {
		this.train_type_code = train_type_code;
	}

	public String getTrain_type_name() {
		return train_type_name;
	}

	public void setTrain_type_name(String train_type_name) {
		this.train_type_name = train_type_name;
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

	public Integer getDept_no() {
		return dept_no;
	}

	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTrain_title() {
		return train_title;
	}

	public void setTrain_title(String train_title) {
		this.train_title = train_title;
	}

	public String getTrain_way_code() {
		return train_way_code;
	}

	public void setTrain_way_code(String train_way_code) {
		this.train_way_code = train_way_code;
	}

	public String getTrain_way_name() {
		return train_way_name;
	}

	public void setTrain_way_name(String train_way_name) {
		this.train_way_name = train_way_name;
	}

	public String getTrain_object() {
		return train_object;
	}

	public void setTrain_object(String train_object) {
		this.train_object = train_object;
	}

	public Integer getIs_exam() {
		return is_exam;
	}

	public void setIs_exam(Integer is_exam) {
		this.is_exam = is_exam;
	}

	public String getIs_exam_name() {
		return is_exam_name;
	}

	public void setIs_exam_name(String is_exam_name) {
		this.is_exam_name = is_exam_name;
	}

	public Integer getIs_cert() {
		return is_cert;
	}

	public void setIs_cert(Integer is_cert) {
		this.is_cert = is_cert;
	}

	public String getIs_cert_name() {
		return is_cert_name;
	}

	public void setIs_cert_name(String is_cert_name) {
		this.is_cert_name = is_cert_name;
	}

	public Integer getCredit_hour() {
		return credit_hour;
	}

	public void setCredit_hour(Integer credit_hour) {
		this.credit_hour = credit_hour;
	}

	public Integer getId_exe() {
		return id_exe;
	}

	public void setId_exe(Integer id_exe) {
		this.id_exe = id_exe;
	}

	public String getId_exe_name() {
		return id_exe_name;
	}

	public void setId_exe_name(String id_exe_name) {
		this.id_exe_name = id_exe_name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}
