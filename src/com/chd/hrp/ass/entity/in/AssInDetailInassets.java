/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.in;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 资产入库明细(专用设备)
 * @Table:
 * ASS_IN_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

   
public class AssInDetailInassets implements Serializable {   

	
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
	 * 业务单号
	 */
	private String ass_in_no;
	
	/**
	 * 资产ID
	 */
	private String ass_id;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
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

	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	private String ass_code;
	
	private String ass_name;
	private String ass_card_no;
	private Double add_depre;
	private Double add_manage_depre;
	private Double cur_money;
	private Double fore_money;
	
	private String reg_no;
	
	private String reg_name;
	
	
	
	public String getReg_name() {
		return reg_name;
	}

	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	
	
	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	
	public String getAss_card_no() {
		return ass_card_no;
	}

	public void setAss_card_no(String ass_card_no) {
		this.ass_card_no = ass_card_no;
	}

	public Double getAdd_depre() {
		return add_depre;
	}

	public void setAdd_depre(Double add_depre) {
		this.add_depre = add_depre;
	}

	public Double getAdd_manage_depre() {
		return add_manage_depre;
	}

	public void setAdd_manage_depre(Double add_manage_depre) {
		this.add_manage_depre = add_manage_depre;
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

	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 生产厂家ID
	 */
	private String fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	private String fac_code;
	
	private String fac_name;
	
	/**
	 * 计量单位
	 */
	private String unit_code;
	
	private String unit_name;
	
	/**
	 * 入库数量
	 */
	private Integer in_amount;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String ass_purpose;
	
	private String ass_purpose_name;
	
	
	
	
	
	
   public String getAss_purpose() {
		return ass_purpose;
	}

	public void setAss_purpose(String ass_purpose) {
		this.ass_purpose = ass_purpose;
	}

	public String getAss_purpose_name() {
		return ass_purpose_name;
	}

	public void setAss_purpose_name(String ass_purpose_name) {
		this.ass_purpose_name = ass_purpose_name;
	}
	
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
	* 设置 业务单号
	* @param value 
	*/
	public void setAss_in_no(String value) {
		this.ass_in_no = value;
	}
	
	/**
	* 获取 业务单号
	* @return String
	*/
	public String getAss_in_no() {
		return this.ass_in_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(String value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public String getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产变更NO
	* @param value 
	*/
	public void setAss_no(Long value) {
		this.ass_no = value;
	}
	
	/**
	* 获取 资产变更NO
	* @return Long
	*/
	public Long getAss_no() {
		return this.ass_no;
	}
	/**
	* 设置 品牌
	* @param value 
	*/
	public void setAss_brand(String value) {
		this.ass_brand = value;
	}
	
	/**
	* 获取 品牌
	* @return String
	*/
	public String getAss_brand() {
		return this.ass_brand;
	}
	/**
	* 设置 生产厂家ID
	* @param value 
	*/
	public void setFac_id(String value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂家ID
	* @return Long
	*/
	public String getFac_id() {
		return this.fac_id;
	}
	/**
	* 设置 生产厂家NO
	* @param value 
	*/
	public void setFac_no(Long value) {
		this.fac_no = value;
	}
	
	/**
	* 获取 生产厂家NO
	* @return Long
	*/
	public Long getFac_no() {
		return this.fac_no;
	}
	/**
	* 设置 计量单位
	* @param value 
	*/
	public void setUnit_code(String value) {
		this.unit_code = value;
	}
	
	/**
	* 获取 计量单位
	* @return String
	*/
	public String getUnit_code() {
		return this.unit_code;
	}
	/**
	* 设置 入库数量
	* @param value 
	*/
	public void setIn_amount(Integer value) {
		this.in_amount = value;
	}
	
	/**
	* 获取 入库数量
	* @return Integer
	*/
	public Integer getIn_amount() {
		return this.in_amount;
	}
	/**
	* 设置 单价
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 单价
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
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

	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	
}