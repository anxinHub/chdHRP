package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;

/**
 * 
 * @ClassName: HrResearchTotSet
 * @Description:科研满分标准
 * @author zn
 * @date 2018年1月24日 上午9:36:57
 * 
 *
 */
public class HrResearchTotSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String year;

	/**
	 * 学术荣誉满分标准
	 */
	private Double acade_honor;

	/**
	 * 学术地位满分标准
	 */
	private Double acade_status;

	/**
	 * 科研成果满分标准
	 */
	private Double proj;

	/**
	 * 论文满分标准
	 */
	private Double paper;

	private String note;

	public HrResearchTotSet() {
		this.acade_honor = 0D;
		this.acade_status = 0D;
		this.proj = 0D;
		this.paper = 0D;
	}

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

	public Double getAcade_honor() {
		return acade_honor;
	}

	public void setAcade_honor(Double acade_honor) {
		this.acade_honor = acade_honor;
	}

	public Double getAcade_status() {
		return acade_status;
	}

	public void setAcade_status(Double acade_status) {
		this.acade_status = acade_status;
	}

	public Double getProj() {
		return proj;
	}

	public void setProj(Double proj) {
		this.proj = proj;
	}

	public Double getPaper() {
		return paper;
	}

	public void setPaper(Double paper) {
		this.paper = paper;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getTotScore() {
		return this.acade_honor + this.acade_status + this.proj + this.paper;
	}

}
