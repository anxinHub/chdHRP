package com.chd.hrp.pac.entity.skht.pactinfo;

import java.io.Serializable;

/**
 * 付款合同明细资金来源
 * 
 * @author haotong
 * @date 2018年9月12日 下午2:01:17
 */
public class PactDetSourSKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1983736965637060810L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private Integer detail_id;
	private Integer source_id;
	private Double budg_money;
	private Double buy_money;

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

	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}

	public Integer getSource_id() {
		return source_id;
	}

	public void setSource_id(Integer source_id) {
		this.source_id = source_id;
	}

	public Double getBudg_money() {
		return budg_money;
	}

	public void setBudg_money(Double budg_money) {
		this.budg_money = budg_money;
	}

	public Double getBuy_money() {
		return buy_money;
	}

	public void setBuy_money(Double buy_money) {
		this.buy_money = buy_money;
	}

}
