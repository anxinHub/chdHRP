package com.chd.hrp.pac.entity.skht.pactinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款合同付款计划
 * 
 * @author haotong
 * @date 2018年9月12日 下午2:00:33
 */
public class PactPlanSKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4427117363579749078L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private Integer plan_detail_id;
	private Integer rec_id;
	private String summary;
	private Date rec_date;
	private String rec_cond;
	private Integer source_id;
	private Double plan_money;

	private String rec_cond_name;
	private String source_name;

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public Integer getPlan_detail_id() {
		return plan_detail_id;
	}

	public void setPlan_detail_id(Integer plan_detail_id) {
		this.plan_detail_id = plan_detail_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getSource_id() {
		return source_id;
	}

	public void setSource_id(Integer source_id) {
		this.source_id = source_id;
	}

	public Double getPlan_money() {
		return plan_money;
	}

	public void setPlan_money(Double plan_money) {
		this.plan_money = plan_money;
	}

	public Integer getRec_id() {
		return rec_id;
	}

	public void setRec_id(Integer rec_id) {
		this.rec_id = rec_id;
	}

	public Date getRec_date() {
		return rec_date;
	}

	public void setRec_date(Date rec_date) {
		this.rec_date = rec_date;
	}

	public String getRec_cond() {
		return rec_cond;
	}

	public void setRec_cond(String rec_cond) {
		this.rec_cond = rec_cond;
	}

	public String getRec_cond_name() {
		return rec_cond_name;
	}

	public void setRec_cond_name(String rec_cond_name) {
		this.rec_cond_name = rec_cond_name;
	}

}
