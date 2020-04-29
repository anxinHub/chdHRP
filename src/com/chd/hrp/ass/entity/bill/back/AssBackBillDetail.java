/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.bill.back;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 红冲发票明细表
 * @Table:
 * ASS_BACK_BILL_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssBackBillDetail implements Serializable {

	
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
	 * 发票流水号
	 */
	private String bill_no;
	
	/**
	 * 资产性质
	 */
	private String naturs_code;
	
	private String naturs_name;
	
	private String ass_nature;
	
	/**
	 * 卡片号
	 */
	private String ass_card_no;
	
	private String ass_id;
	
	private String ass_no;
	
	private String ass_code;
	
	private String ass_name;
	
	/**
	 * 开票金额
	 */
	private Double bill_money;
	
	/**
	 * 说明
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public String getAss_nature() {
		return ass_nature;
	}
	
	public void setAss_nature(String ass_nature) {
		this.ass_nature = ass_nature;
	}

	public String getNaturs_name() {
		return naturs_name;
	}
	
	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
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
	* 设置 发票流水号
	* @param value 
	*/
	public void setBill_no(String value) {
		this.bill_no = value;
	}
	
	/**
	* 获取 发票流水号
	* @return String
	*/
	public String getBill_no() {
		return this.bill_no;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setNaturs_code(String value) {
		this.naturs_code = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getNaturs_code() {
		return this.naturs_code;
	}
	/**
	* 设置 卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 开票金额
	* @param value 
	*/
	public void setBill_money(Double value) {
		this.bill_money = value;
	}
	
	/**
	* 获取 开票金额
	* @return Double
	*/
	public Double getBill_money() {
		return this.bill_money;
	}
	/**
	* 设置 说明
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 说明
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