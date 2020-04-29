package com.chd.hrp.hr.entity.salarymanagement.socialSecurityManage;
/**
 * 【薪资管理-社保管理】：缴费基数公式
 * @author yang
 *
 */
public class HrInsurBaseCal {

	private Long cal_id;
	
	private Integer group_id;
	private Integer hos_id;
	
	private String kind_code;
	private String kind_name;
	
	private String cal_name;
	private String cal_eng;
	
	private String note;
	
	public HrInsurBaseCal(){}

	public Long getCal_id() {
		return cal_id;
	}

	public void setCal_id(Long cal_id) {
		this.cal_id = cal_id;
	}

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

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public String getCal_name() {
		return cal_name;
	}

	public void setCal_name(String cal_name) {
		this.cal_name = cal_name;
	}

	public String getCal_eng() {
		return cal_eng;
	}

	public void setCal_eng(String cal_eng) {
		this.cal_eng = cal_eng;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
