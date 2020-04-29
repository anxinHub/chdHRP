/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.entity.sell.in;

import java.io.Serializable;

/**
 * 
 * @Description: 050901 资产有偿调拨入库明细(土地)
 * @Table: ASS_SELL_IN_DETAIL_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class AssSellInDetailLand implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;

	/**
	 * 医院ID
	 */
	private Long hos_id;

	/**
	 * 账套编码
	 */
	private String copy_code;

	/**
	 * 调拨单号
	 */
	private String sell_in_no;

	/**
	 * 资产ID
	 */
	private String ass_id;

	/**
	 * 资产变更NO
	 */
	private Long ass_no;

	private String ass_code;

	private String ass_name;

	/**
	 * 原始卡片编号
	 */
	private String ass_ori_card_no;

	/**
	 * 资产原值
	 */
	private Double price;

	/**
	 * 累计折旧
	 */
	private Double add_depre;

	/**
	 * 累计折旧月份
	 */
	private Integer add_depre_month;

	/**
	 * 资产净值
	 */
	private Double cur_money;

	/**
	 * 预留残值
	 */
	private Double fore_money;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getSell_in_no() {
		return sell_in_no;
	}

	public void setSell_in_no(String sell_in_no) {
		this.sell_in_no = sell_in_no;
	}

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public Long getAss_no() {
		return ass_no;
	}

	public void setAss_no(Long ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_ori_card_no() {
		return ass_ori_card_no;
	}

	public void setAss_ori_card_no(String ass_ori_card_no) {
		this.ass_ori_card_no = ass_ori_card_no;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAdd_depre() {
		return add_depre;
	}

	public void setAdd_depre(Double add_depre) {
		this.add_depre = add_depre;
	}

	public Integer getAdd_depre_month() {
		return add_depre_month;
	}

	public void setAdd_depre_month(Integer add_depre_month) {
		this.add_depre_month = add_depre_month;
	}

	public Double getCur_money() {
		return cur_money;
	}

	public void setCur_money(Double cur_money) {
		this.cur_money = cur_money;
	}

	public Double getFore_money() {
		return fore_money;
	}

	public void setFore_money(Double fore_money) {
		this.fore_money = fore_money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}