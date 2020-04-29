/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 供应商字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccSupAttr implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 供应商ID
	 */
	private double sup_id;
	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 开户银行
	 */
	private String bank_name;
	/**
	 * 银行账号
	 */
	private String bank_number;
	/**
	 * 法人
	 */
	private String legal;
	/**
	 * 纳税人登记号
	 */
	private String regis_no;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * EMAIL
	 */
	private String email;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 邮政编码
	 */
	private String zip_code;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String note;
	
	private String sup_code;
	
	private String type_code;
	
	private String type_name;
	
	private String sup_name;
	
	private Integer is_stop;
	
	private Integer is_disable;
	
	private String spell_code;
	
	private String wbx_code;
	
	private Integer is_mat;
	
	private Integer is_med;
	
	private Integer is_ass;
	
	private Integer is_sup;
	
	private Long range_id;

	
	
	public Long getRange_id() {
		return range_id;
	}
	public void setRange_id(Long range_id) {
		this.range_id = range_id;
	}
	/**
	 * 设置 供应商ID
	 */
	public void setSup_id(double value) {
		this.sup_id = value;
	}
	/**
	 * 获取 供应商ID
	 */	
	public double getSup_id() {
		return this.sup_id;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(double value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public double getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(double value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public double getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 开户银行
	 */
	public void setBank_name(String value) {
		this.bank_name = value;
	}
	/**
	 * 获取 开户银行
	 */	
	public String getBank_name() {
		return this.bank_name;
	}
	/**
	 * 设置 银行账号
	 */
	public void setBank_number(String value) {
		this.bank_number = value;
	}
	/**
	 * 获取 银行账号
	 */	
	public String getBank_number() {
		return this.bank_number;
	}
	/**
	 * 设置 法人
	 */
	public void setLegal(String value) {
		this.legal = value;
	}
	/**
	 * 获取 法人
	 */	
	public String getLegal() {
		return this.legal;
	}
	/**
	 * 设置 纳税人登记号
	 */
	public void setRegis_no(String value) {
		this.regis_no = value;
	}
	/**
	 * 获取 纳税人登记号
	 */	
	public String getRegis_no() {
		return this.regis_no;
	}
	/**
	 * 设置 电话
	 */
	public void setPhone(String value) {
		this.phone = value;
	}
	/**
	 * 获取 电话
	 */	
	public String getPhone() {
		return this.phone;
	}
	/**
	 * 设置 手机
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}
	/**
	 * 获取 手机
	 */	
	public String getMobile() {
		return this.mobile;
	}
	/**
	 * 设置 联系人
	 */
	public void setContact(String value) {
		this.contact = value;
	}
	/**
	 * 获取 联系人
	 */	
	public String getContact() {
		return this.contact;
	}
	/**
	 * 设置 传真
	 */
	public void setFax(String value) {
		this.fax = value;
	}
	/**
	 * 获取 传真
	 */	
	public String getFax() {
		return this.fax;
	}
	/**
	 * 设置 EMAIL
	 */
	public void setEmail(String value) {
		this.email = value;
	}
	/**
	 * 获取 EMAIL
	 */	
	public String getEmail() {
		return this.email;
	}
	/**
	 * 设置 地区
	 */
	public void setRegion(String value) {
		this.region = value;
	}
	/**
	 * 获取 地区
	 */	
	public String getRegion() {
		return this.region;
	}
	/**
	 * 设置 邮政编码
	 */
	public void setZip_code(String value) {
		this.zip_code = value;
	}
	/**
	 * 获取 邮政编码
	 */	
	public String getZip_code() {
		return this.zip_code;
	}
	/**
	 * 设置 地址
	 */
	public void setAddress(String value) {
		this.address = value;
	}
	/**
	 * 获取 地址
	 */	
	public String getAddress() {
		return this.address;
	}
	/**
	 * 设置 备注
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 备注
	 */	
	public String getNote() {
		return this.note;
	}
	public String getSup_code() {
		return sup_code;
	}
	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public Integer getIs_mat() {
		return is_mat;
	}
	public void setIs_mat(Integer is_mat) {
		this.is_mat = is_mat;
	}
	public Integer getIs_med() {
		return is_med;
	}
	public void setIs_med(Integer is_med) {
		this.is_med = is_med;
	}
	public Integer getIs_ass() {
		return is_ass;
	}
	public void setIs_ass(Integer is_ass) {
		this.is_ass = is_ass;
	}
	public Integer getIs_sup() {
		return is_sup;
	}
	public void setIs_sup(Integer is_sup) {
		this.is_sup = is_sup;
	}
	
	public Integer getIs_disable() {
		return is_disable;
	}
	public void setIs_disable(Integer is_disable) {
		this.is_disable = is_disable;
	}
}