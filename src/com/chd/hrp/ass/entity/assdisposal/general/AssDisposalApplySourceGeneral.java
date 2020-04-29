/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.entity.assdisposal.general;

import java.io.Serializable;

/**
 * 
 * @Description: 050805 资产处置申请单资金来源(一般设备)
 * @Table: ASS_DISPOSAL_A_SOURCE_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class AssDisposalApplySourceGeneral implements Serializable {

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
	 * 处置单号
	 */
	private String dis_a_no;

	/**
	 * 卡片编号
	 */
	private String ass_card_no;

	/**
	 * 资金来源
	 */
	private Long source_id;

	/**
	 * 资产原值
	 */
	private Double price;

	/**
	 * 本期折旧
	 */
	private Double now_depre;

	/**
	 * 本期分摊
	 */
	private Double now_manage_depre;

	/**
	 * 累计折旧
	 */
	private Double add_depre;

	/**
	 * 累计分摊
	 */
	private Double add_manage_depre;

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

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 处置单号
	 * 
	 * @param value
	 */
	public void setDis_a_no(String value) {
		this.dis_a_no = value;
	}

	/**
	 * 获取 处置单号
	 * 
	 * @return String
	 */
	public String getDis_a_no() {
		return this.dis_a_no;
	}

	/**
	 * 设置 卡片编号
	 * 
	 * @param value
	 */
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}

	/**
	 * 获取 卡片编号
	 * 
	 * @return String
	 */
	public String getAss_card_no() {
		return this.ass_card_no;
	}

	/**
	 * 设置 资金来源
	 * 
	 * @param value
	 */
	public void setSource_id(Long value) {
		this.source_id = value;
	}

	/**
	 * 获取 资金来源
	 * 
	 * @return Long
	 */
	public Long getSource_id() {
		return this.source_id;
	}

	/**
	 * 设置 资产原值
	 * 
	 * @param value
	 */
	public void setPrice(Double value) {
		this.price = value;
	}

	/**
	 * 获取 资产原值
	 * 
	 * @return Double
	 */
	public Double getPrice() {
		return this.price;
	}

	/**
	 * 设置 本期折旧
	 * 
	 * @param value
	 */
	public void setNow_depre(Double value) {
		this.now_depre = value;
	}

	/**
	 * 获取 本期折旧
	 * 
	 * @return Double
	 */
	public Double getNow_depre() {
		return this.now_depre;
	}

	/**
	 * 设置 本期分摊
	 * 
	 * @param value
	 */
	public void setNow_manage_depre(Double value) {
		this.now_manage_depre = value;
	}

	/**
	 * 获取 本期分摊
	 * 
	 * @return Double
	 */
	public Double getNow_manage_depre() {
		return this.now_manage_depre;
	}

	/**
	 * 设置 累计折旧
	 * 
	 * @param value
	 */
	public void setAdd_depre(Double value) {
		this.add_depre = value;
	}

	/**
	 * 获取 累计折旧
	 * 
	 * @return Double
	 */
	public Double getAdd_depre() {
		return this.add_depre;
	}

	/**
	 * 设置 累计分摊
	 * 
	 * @param value
	 */
	public void setAdd_manage_depre(Double value) {
		this.add_manage_depre = value;
	}

	/**
	 * 获取 累计分摊
	 * 
	 * @return Double
	 */
	public Double getAdd_manage_depre() {
		return this.add_manage_depre;
	}

	/**
	 * 设置 资产净值
	 * 
	 * @param value
	 */
	public void setCur_money(Double value) {
		this.cur_money = value;
	}

	/**
	 * 获取 资产净值
	 * 
	 * @return Double
	 */
	public Double getCur_money() {
		return this.cur_money;
	}

	/**
	 * 设置 预留残值
	 * 
	 * @param value
	 */
	public void setFore_money(Double value) {
		this.fore_money = value;
	}

	/**
	 * 获取 预留残值
	 * 
	 * @return Double
	 */
	public Double getFore_money() {
		return this.fore_money;
	}

	/**
	 * 设置 备注
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取 备注
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}