/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.bid;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050401 招标资产明细
 * @Table:
 * ASS_BID_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssBidDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 招标ID
	 */
	private Long bid_id;
	
	/**
	 * 招标明细ID
	 */
	private Long bid_detail_id;
	
	/**
	 * 招标编号
	 */
	private String bid_no;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	private String ass_code;
	
	private String ass_name;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 数量
	 */
	private Integer ass_num;
	
	/**
	 * 价格
	 */
	private Double ass_price;
	
	/**
	 * 金额
	 */
	private Double ass_money;
	
	/**
	 * 生产厂家ID
	 */
	private Long fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	private String fac_code;
	
	private String fac_name;
	
	/**
	 * 备注
	 */
	private String bid_note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String ass_nature;
	
	
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

	public String getAss_nature() {
		return ass_nature;
	}

	public void setAss_nature(String ass_nature) {
		this.ass_nature = ass_nature;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 招标ID
	* @param value 
	*/
	public void setBid_id(Long value) {
		this.bid_id = value;
	}
	
	/**
	* 获取 招标ID
	* @return Long
	*/
	public Long getBid_id() {
		return this.bid_id;
	}
	/**
	* 设置 招标明细ID
	* @param value 
	*/
	public void setBid_detail_id(Long value) {
		this.bid_detail_id = value;
	}
	
	/**
	* 获取 招标明细ID
	* @return Long
	*/
	public Long getBid_detail_id() {
		return this.bid_detail_id;
	}
	/**
	* 设置 招标编号
	* @param value 
	*/
	public void setBid_no(String value) {
		this.bid_no = value;
	}
	
	/**
	* 获取 招标编号
	* @return String
	*/
	public String getBid_no() {
		return this.bid_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(Long value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public Long getAss_id() {
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
	* 设置 规格
	* @param value 
	*/
	public void setAss_spec(String value) {
		this.ass_spec = value;
	}
	
	/**
	* 获取 规格
	* @return String
	*/
	public String getAss_spec() {
		return this.ass_spec;
	}
	/**
	* 设置 型号
	* @param value 
	*/
	public void setAss_model(String value) {
		this.ass_model = value;
	}
	
	/**
	* 获取 型号
	* @return String
	*/
	public String getAss_model() {
		return this.ass_model;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setAss_num(Integer value) {
		this.ass_num = value;
	}
	
	/**
	* 获取 数量
	* @return Integer
	*/
	public Integer getAss_num() {
		return this.ass_num;
	}
	/**
	* 设置 价格
	* @param value 
	*/
	public void setAss_price(Double value) {
		this.ass_price = value;
	}
	
	/**
	* 获取 价格
	* @return Double
	*/
	public Double getAss_price() {
		return this.ass_price;
	}
	/**
	* 设置 金额
	* @param value 
	*/
	public void setAss_money(Double value) {
		this.ass_money = value;
	}
	
	/**
	* 获取 金额
	* @return Double
	*/
	public Double getAss_money() {
		return this.ass_money;
	}
	/**
	* 设置 生产厂家ID
	* @param value 
	*/
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂家ID
	* @return Long
	*/
	public Long getFac_id() {
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
	* 设置 备注
	* @param value 
	*/
	public void setBid_note(String value) {
		this.bid_note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getBid_note() {
		return this.bid_note;
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