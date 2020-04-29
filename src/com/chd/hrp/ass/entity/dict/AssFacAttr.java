
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050115 生产厂商附属表
 * @Table:
 * ASS_FAC_ATTR
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssFacAttr implements Serializable {

	
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
	 * 生产厂商ID
	 */
	private Long fac_id;
	/**
	 * 生产厂商变更ID
	 */
	private Long fac_no;
	/**
	 * 生产厂商名称
	 */
	private String fac_name;
	private String fac_code;
	
	private String mod_code;
	private String mod_name;
	
	private String spell_code;
	private String wbx_code;
	private Long fac_sort;
	
	/**
	 * 厂商类别名称
	 */
	private String type_code;
	private String type_name;
	
	
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
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	private Integer is_mat;
	private Integer is_ass;
	private Integer is_med;
	private Integer is_sup;

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
	* 设置 生产厂商ID
	* @param value 
	*/
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂商ID
	* @return Long
	*/
	public Long getFac_id() {
		return this.fac_id;
	}
	/**
	 * 获取生产厂商变更ID
	 * @return
	 */
	public Long getFac_no() {
		return fac_no;
	}

	public void setFac_no(Long fac_no) {
		this.fac_no = fac_no;
	}

	/**
	* 设置 开户银行
	* @param value 
	*/
	public void setBank_name(String value) {
		this.bank_name = value;
	}
	
	/**
	* 获取 开户银行
	* @return String
	*/
	public String getBank_name() {
		return this.bank_name;
	}
	/**
	* 设置 银行账号
	* @param value 
	*/
	public void setBank_number(String value) {
		this.bank_number = value;
	}
	
	/**
	* 获取 银行账号
	* @return String
	*/
	public String getBank_number() {
		return this.bank_number;
	}
	/**
	* 设置 法人
	* @param value 
	*/
	public void setLegal(String value) {
		this.legal = value;
	}
	
	/**
	* 获取 法人
	* @return String
	*/
	public String getLegal() {
		return this.legal;
	}
	/**
	* 设置 纳税人登记号
	* @param value 
	*/
	public void setRegis_no(String value) {
		this.regis_no = value;
	}
	
	/**
	* 获取 纳税人登记号
	* @return String
	*/
	public String getRegis_no() {
		return this.regis_no;
	}
	/**
	* 设置 电话
	* @param value 
	*/
	public void setPhone(String value) {
		this.phone = value;
	}
	
	/**
	* 获取 电话
	* @return String
	*/
	public String getPhone() {
		return this.phone;
	}
	/**
	* 设置 手机
	* @param value 
	*/
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	/**
	* 获取 手机
	* @return String
	*/
	public String getMobile() {
		return this.mobile;
	}
	/**
	* 设置 联系人
	* @param value 
	*/
	public void setContact(String value) {
		this.contact = value;
	}
	
	/**
	* 获取 联系人
	* @return String
	*/
	public String getContact() {
		return this.contact;
	}
	/**
	* 设置 传真
	* @param value 
	*/
	public void setFax(String value) {
		this.fax = value;
	}
	
	/**
	* 获取 传真
	* @return String
	*/
	public String getFax() {
		return this.fax;
	}
	/**
	* 设置 EMAIL
	* @param value 
	*/
	public void setEmail(String value) {
		this.email = value;
	}
	
	/**
	* 获取 EMAIL
	* @return String
	*/
	public String getEmail() {
		return this.email;
	}
	/**
	* 设置 地区
	* @param value 
	*/
	public void setRegion(String value) {
		this.region = value;
	}
	
	/**
	* 获取 地区
	* @return String
	*/
	public String getRegion() {
		return this.region;
	}
	/**
	* 设置 邮政编码
	* @param value 
	*/
	public void setZip_code(String value) {
		this.zip_code = value;
	}
	
	/**
	* 获取 邮政编码
	* @return String
	*/
	public String getZip_code() {
		return this.zip_code;
	}
	/**
	* 设置 地址
	* @param value 
	*/
	public void setAddress(String value) {
		this.address = value;
	}
	
	/**
	* 获取 地址
	* @return String
	*/
	public String getAddress() {
		return this.address;
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

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	/**
	 * @return the fac_code
	 */
	public String getFac_code() {
		return fac_code;
	}

	/**
	 * @param fac_code the fac_code to set
	 */
	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	public Integer getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	
	/**
	 * 获取 厂商类别名称
	 */
	public String getType_name() {
		return type_name;
	}
	
	/**
	 * 设置 厂商类别名称
	 */
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
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

	public Long getFac_sort() {
		return fac_sort;
	}

	public void setFac_sort(Long fac_sort) {
		this.fac_sort = fac_sort;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	/**
	 * @return the is_mat
	 */
	public Integer getIs_mat() {
		return is_mat;
	}

	/**
	 * @param is_mat the is_mat to set
	 */
	public void setIs_mat(Integer is_mat) {
		this.is_mat = is_mat;
	}

	/**
	 * @return the is_ass
	 */
	public Integer getIs_ass() {
		return is_ass;
	}

	/**
	 * @param is_ass the is_ass to set
	 */
	public void setIs_ass(Integer is_ass) {
		this.is_ass = is_ass;
	}

	/**
	 * @return the is_med
	 */
	public Integer getIs_med() {
		return is_med;
	}

	/**
	 * @param is_med the is_med to set
	 */
	public void setIs_med(Integer is_med) {
		this.is_med = is_med;
	}

	/**
	 * @return the is_sup
	 */
	public Integer getIs_sup() {
		return is_sup;
	}

	/**
	 * @param is_sup the is_sup to set
	 */
	public void setIs_sup(Integer is_sup) {
		this.is_sup = is_sup;
	}
	
	
}