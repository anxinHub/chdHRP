/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.sell.out;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单明细(土地)
 * @Table:
 * ASS_SELL_OUT_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssSellOutDetailLand implements Serializable {

	
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
	private String sell_out_no;
	
	/**
	 * 卡片编号
	 */
	private String ass_card_no;
	
	private String ass_id;
	
	private String ass_no;
	
	private String ass_code;
	
	private String ass_name;
	
	private String fac_id;
	
	private String fac_no;
	
	private String ass_brand;
	
	private String ass_spec;
	
	private String ass_model;
	
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
	 * 调拨价格
	 */
	private Double sell_price;
	
	/**
	 * sell_price - CUR_MONEY+FORE_MONEY
	 */
	private Double dispose_income;
	
	/**
	 * 应缴税费
	 */
	private Double dispose_tax;
	
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
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 调拨单号
	* @param value 
	*/
	public void setSell_out_no(String value) {
		this.sell_out_no = value;
	}
	
	/**
	* 获取 调拨单号
	* @return String
	*/
	public String getSell_out_no() {
		return this.sell_out_no;
	}
	/**
	* 设置 卡片编号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 资产原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 资产原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 本期折旧
	* @param value 
	*/
	public void setNow_depre(Double value) {
		this.now_depre = value;
	}
	
	/**
	* 获取 本期折旧
	* @return Double
	*/
	public Double getNow_depre() {
		return this.now_depre;
	}
	/**
	* 设置 本期分摊
	* @param value 
	*/
	public void setNow_manage_depre(Double value) {
		this.now_manage_depre = value;
	}
	
	/**
	* 获取 本期分摊
	* @return Double
	*/
	public Double getNow_manage_depre() {
		return this.now_manage_depre;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setAdd_depre(Double value) {
		this.add_depre = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getAdd_depre() {
		return this.add_depre;
	}
	/**
	* 设置 累计分摊
	* @param value 
	*/
	public void setAdd_manage_depre(Double value) {
		this.add_manage_depre = value;
	}
	
	/**
	* 获取 累计分摊
	* @return Double
	*/
	public Double getAdd_manage_depre() {
		return this.add_manage_depre;
	}
	/**
	* 设置 累计折旧月份
	* @param value 
	*/
	public void setAdd_depre_month(Integer value) {
		this.add_depre_month = value;
	}
	
	/**
	* 获取 累计折旧月份
	* @return Integer
	*/
	public Integer getAdd_depre_month() {
		return this.add_depre_month;
	}
	/**
	* 设置 资产净值
	* @param value 
	*/
	public void setCur_money(Double value) {
		this.cur_money = value;
	}
	
	/**
	* 获取 资产净值
	* @return Double
	*/
	public Double getCur_money() {
		return this.cur_money;
	}
	/**
	* 设置 预留残值
	* @param value 
	*/
	public void setFore_money(Double value) {
		this.fore_money = value;
	}
	
	/**
	* 获取 预留残值
	* @return Double
	*/
	public Double getFore_money() {
		return this.fore_money;
	}
	/**
	* 设置 调拨价格
	* @param value 
	*/
	public void setSell_price(Double value) {
		this.sell_price = value;
	}
	
	/**
	* 获取 调拨价格
	* @return Double
	*/
	public Double getSell_price() {
		return this.sell_price;
	}
	/**
	* 设置 sell_price - CUR_MONEY+FORE_MONEY
	* @param value 
	*/
	public void setDispose_income(Double value) {
		this.dispose_income = value;
	}
	
	/**
	* 获取 sell_price - CUR_MONEY+FORE_MONEY
	* @return Double
	*/
	public Double getDispose_income() {
		return this.dispose_income;
	}
	/**
	* 设置 应缴税费
	* @param value 
	*/
	public void setDispose_tax(Double value) {
		this.dispose_tax = value;
	}
	
	/**
	* 获取 应缴税费
	* @return Double
	*/
	public Double getDispose_tax() {
		return this.dispose_tax;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
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

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_no() {
		return ass_no;
	}

	public void setAss_no(String ass_no) {
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

	public String getFac_id() {
		return fac_id;
	}

	public void setFac_id(String fac_id) {
		this.fac_id = fac_id;
	}

	public String getFac_no() {
		return fac_no;
	}

	public void setFac_no(String fac_no) {
		this.fac_no = fac_no;
	}

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}
	
}