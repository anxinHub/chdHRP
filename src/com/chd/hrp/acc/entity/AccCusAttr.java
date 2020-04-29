package com.chd.hrp.acc.entity;

import java.io.Serializable;

public class AccCusAttr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private Long cus_id;

	private String bank_name;

	private String bank_number;

	private String legal;

	private String regis_no;

	private String phone;

	private String mobile;

	private String contact;

	private String fax;

	private String email;

	private String region;

	private String zip_code;

	private String address;

	private String note;
	
	private String cus_code;
	
	private String cus_name;
	
	private String type_code;
	
	private String type_name;
	
	private Integer is_stop;
	
	private Integer is_disable;

	private String spell_code;
	
	private String wbx_code;
	
	private Integer is_mat;
	
	private Integer is_med;
	
	private Integer is_ass;
	
	private Integer is_sup;
	
	private String  tmop_code;//   --结算方式
    private String  dang_code;// varchar2,   --险种
    private String  aff_code;//  varchar2,   --关联方
    private String  inst_code;// varchar2,   --机构
    private Long range_id;

	public Long getRange_id() {
		return range_id;
	}

	public void setRange_id(Long range_id) {
		this.range_id = range_id;
	}

	public String getTmop_code() {
		return tmop_code;
	}

	public void setTmop_code(String tmop_code) {
		this.tmop_code = tmop_code;
	}

	public String getDang_code() {
		return dang_code;
	}

	public void setDang_code(String dang_code) {
		this.dang_code = dang_code;
	}

	public String getAff_code() {
		return aff_code;
	}

	public void setAff_code(String aff_code) {
		this.aff_code = aff_code;
	}

	public String getInst_code() {
		return inst_code;
	}

	public void setInst_code(String inst_code) {
		this.inst_code = inst_code;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getCus_id() {
		return cus_id;
	}

	public void setCus_id(Long cus_id) {
		this.cus_id = cus_id;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_number() {
		return bank_number;
	}

	public void setBank_number(String bank_number) {
		this.bank_number = bank_number;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}


	public String getRegis_no() {
		return regis_no;
	}

	public void setRegis_no(String regis_no) {
		this.regis_no = regis_no;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCus_code() {
		return cus_code;
	}

	public void setCus_code(String cus_code) {
		this.cus_code = cus_code;
	}

	public String getCus_name() {
		return cus_name;
	}

	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
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
