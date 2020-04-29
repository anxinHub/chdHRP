/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 生产厂商银行信息表
 * @Table:
 * HOS_FAC_BANK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssHosFacBank implements Serializable {

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
	private Long fac_id;
	
	/**
	 * 生产厂商编码
	 */
	private String fac_code;
	
	/**
	 * 生产厂商名称
	 */
	private String fac_name;
	
	/**
	 * 银行账号
	 */
	private String bank_no;
	
	/**
	 * 开户银行
	 */
	private String bank_name;

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
	
	public Long getFac_id() {
		return fac_id;
	}
	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
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
}