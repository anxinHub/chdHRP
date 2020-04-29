
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
 * 050113 供应商附属表
 * @Table:
 * ASS_SUB_ATTR
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssSupAttr implements Serializable {

	
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
	private String sup_code;
	private String sup_name;
	private String spell_code;
	private String wbx_code;
	
	private Long sort_code;
	private String type_code;
	private String type_name;
	private String mod_code;
	private String mod_name;
	private Long is_flag;
	
	private String pay_code;
	private String pay_name;
	/**
	 * 开户银行
	 */
	private String bank_name;
	
	/**
	 * 银行账号
	 */
	private String bank_no;
	
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
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	 * 供应商简称
	 */
	private String sup_alias;
	
	/**
	 * 所属行业
	 */
	private String ven_trade;
	
	/**
	 * 省
	 */
	private String prov;
	
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 扣率
	 */
	private Double ven_dis_rate;
	
	/**
	 * 付款条件
	 */
	private String ven_pay_con;
	
	/**
	 * 专营业务员
	 */
	private String ven_person;
	
	/**
	 * 到货地址
	 */
	private String ven_dir_address;
	
	/**
	 * 信用等级
	 */
	private String ven_cre_grade;
	
	/**
	 * 停用日期
	 */
	private Date end_date;
	/**
	 * 是否区县
	 */
	private Integer is_count;
	
	/**
	 * 分管部门
	 */
	private Long dept_id;
	
	private String dept_code;
	
	/**
	 * 信用额度
	 */
	private Double ven_cre_line;
	
	/**
	 * 单价是否含税
	 */
	private Integer bven_tax;
	
	/**
	 * 发展日期
	 */
	private Date ven_dev_date;
	
	/**
	 * 主要产品
	 */
	private String products;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	/**
	 * 营业执照
	 */
	private String business_charter;
	
	/**
	 * 使用频度
	 */
	private Long frequency;
	
	private Integer is_mat;
	private Integer is_ass;
	private Integer is_med;
	private Integer is_sup;
	private Integer is_delivery;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Double
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
	* @return Double
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Double
	*/
	public Long getSup_id() {
		return this.sup_id;
	}

	/**
	 * @return the sup_code
	 */
	public String getSup_code() {
		return sup_code;
	}

	/**
	 * @param sup_code the sup_code to set
	 */
	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}
	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
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
	public void setBank_no(String value) {
		this.bank_no = value;
	}
	
	/**
	* 获取 银行账号
	* @return String
	*/
	public String getBank_no() {
		return this.bank_no;
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

	public String getSup_alias() {
		return sup_alias;
	}

	public void setSup_alias(String sup_alias) {
		this.sup_alias = sup_alias;
	}

	public String getVen_trade() {
		return ven_trade;
	}

	public void setVen_trade(String ven_trade) {
		this.ven_trade = ven_trade;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getVen_dis_rate() {
		return ven_dis_rate;
	}

	public void setVen_dis_rate(Double ven_dis_rate) {
		this.ven_dis_rate = ven_dis_rate;
	}

	public String getVen_pay_con() {
		return ven_pay_con;
	}

	public void setVen_pay_con(String ven_pay_con) {
		this.ven_pay_con = ven_pay_con;
	}

	public String getVen_person() {
		return ven_person;
	}

	public void setVen_person(String ven_person) {
		this.ven_person = ven_person;
	}

	public String getVen_dir_address() {
		return ven_dir_address;
	}

	public void setVen_dir_address(String ven_dir_address) {
		this.ven_dir_address = ven_dir_address;
	}

	public String getVen_cre_grade() {
		return ven_cre_grade;
	}

	public void setVen_cre_grade(String ven_cre_grade) {
		this.ven_cre_grade = ven_cre_grade;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Integer getIs_count() {
		return is_count;
	}

	public void setIs_count(Integer is_count) {
		this.is_count = is_count;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Double getVen_cre_line() {
		return ven_cre_line;
	}

	public void setVen_cre_line(Double ven_cre_line) {
		this.ven_cre_line = ven_cre_line;
	}

	public Integer getBven_tax() {
		return bven_tax;
	}

	public void setBven_tax(Integer bven_tax) {
		this.bven_tax = bven_tax;
	}

	public Date getVen_dev_date() {
		return ven_dev_date;
	}

	public void setVen_dev_date(Date ven_dev_date) {
		this.ven_dev_date = ven_dev_date;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getBusiness_charter() {
		return business_charter;
	}

	public void setBusiness_charter(String business_charter) {
		this.business_charter = business_charter;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
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

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
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

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}

	
    public Long getIs_flag() {
    	return is_flag;
    }

	
    public void setIs_flag(Long is_flag) {
    	this.is_flag = is_flag;
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

	/**
	 * @return the pay_code
	 */
	public String getPay_code() {
		return pay_code;
	}

	/**
	 * @param pay_code the pay_code to set
	 */
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	/**
	 * @return the pay_name
	 */
	public String getPay_name() {
		return pay_name;
	}

	/**
	 * @param pay_name the pay_name to set
	 */
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

	public Integer getIs_delivery() {
		return is_delivery;
	}

	public void setIs_delivery(Integer is_delivery) {
		this.is_delivery = is_delivery;
	}
	
	
}