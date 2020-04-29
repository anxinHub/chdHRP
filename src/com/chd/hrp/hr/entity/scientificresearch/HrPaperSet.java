package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;

public class HrPaperSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String year;

	private String paper_type_code;

	private String paper_type_name;

	private String affect_para;

	private String affect_para_name;

	private Double score;

	private String note;

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPaper_type_code() {
		return paper_type_code;
	}

	public void setPaper_type_code(String paper_type_code) {
		this.paper_type_code = paper_type_code;
	}

	public String getPaper_type_name() {
		return paper_type_name;
	}

	public void setPaper_type_name(String paper_type_name) {
		this.paper_type_name = paper_type_name;
	}

	public String getAffect_para() {
		return affect_para;
	}

	public void setAffect_para(String affect_para) {
		this.affect_para = affect_para;
	}

	public String getAffect_para_name() {
		return affect_para_name;
	}

	public void setAffect_para_name(String affect_para_name) {
		this.affect_para_name = affect_para_name;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
