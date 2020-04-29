package com.chd.hrp.pac.entity.fkht.pactinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款合同付款计划
 * 
 * @author haotong
 * @date 2018年9月12日 下午2:00:33
 */
public class PactPlanFKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4427117363579749078L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private Integer plan_detail_id;
	private Integer pay_id;
	private String summary;
	private Date pay_date;
	private String pay_cond;
	private Integer source_id;
	
	private Double plan_money;

	private String pay_cond_name;
	private String source_name;
	
	/**
	 * 付款类型  1全款、2预付款、3期款、4尾款
	 */
	private Integer pay_type;
	/**
	 * 计划付款比例
	 */
	private Double rate;
	
	/**
	 * 已付金额
	 */
	private Double payed_money;
	

	/**
	 * 已付金额
	 */
	private Integer pay_flag;
	
	/**
	 * 变更号
	 */
	private String change_code;
	
	public Integer getPay_flag() {
		return pay_flag;
	}

	public void setPay_flag(Integer pay_flag) {
		this.pay_flag = pay_flag;
	}
	
	public String getChange_code() {
		return change_code;
	}

	public void setChange_code(String change_code) {
		this.change_code = change_code;
	}

	
	
	

	public String getPay_cond_name() {
		return pay_cond_name;
	}

	public void setPay_cond_name(String pay_cond_name) {
		this.pay_cond_name = pay_cond_name;
	}

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

	public Integer getPay_id() {
		return pay_id;
	}

	public void setPay_id(Integer pay_id) {
		this.pay_id = pay_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public String getPay_cond() {
		return pay_cond;
	}

	public void setPay_cond(String pay_cond) {
		this.pay_cond = pay_cond;
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

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getPayed_money() {
		return payed_money;
	}

	public void setPayed_money(Double payed_money) {
		this.payed_money = payed_money;
	}
	
	
}
