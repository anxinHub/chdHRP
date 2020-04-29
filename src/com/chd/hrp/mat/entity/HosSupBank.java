/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 供应商银行信息表
 * @Table:
 * HOS_SUP_BANK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class HosSupBank implements Serializable {

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
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 供应商编码
	 */
	private String sup_code;
	
	/**
	 * 供应商名称
	 */
	private String sup_name;
	
	/**
	 * 银行账号
	 */
	private String bank_no;
	
	/**
	 * 开户银行
	 */
	private String bank_name;
	
	private Integer bank_area_number;
	
	private String bank_code;
	
	private String bank_area_name;
	
	private String bank_full_name;
	
	/**
	 * 是否默认
	 */
	private Integer is_default;

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
	
	public Long getSup_id() {
		return sup_id;
	}
	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}
	public String getSup_code() {
		return sup_code;
	}
	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public Integer getBank_area_number() {
		return bank_area_number;
	}

	public void setBank_area_number(Integer bank_area_number) {
		this.bank_area_number = bank_area_number;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_area_name() {
		return bank_area_name;
	}

	public void setBank_area_name(String bank_area_name) {
		this.bank_area_name = bank_area_name;
	}

	public String getBank_full_name() {
		return bank_full_name;
	}

	public void setBank_full_name(String bank_full_name) {
		this.bank_full_name = bank_full_name;
	}
	
	
	/**
	* 获取 是否默认
	* @return Integer
	*/
	public Integer getIs_default() {
		return is_default;
	}
	
	/**
	* 设置 是否默认
	*/
	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}
	
}