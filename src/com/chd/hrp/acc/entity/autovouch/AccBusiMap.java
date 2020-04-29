package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class AccBusiMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String acc_year;
	
	private String mod_code;

	private String meta_code;

	private String type_id;
	
	private String sub_type_id;
	
	private String subj_code;
	
	private String out_code;
	
	private Integer source_nature_code;
	
	private String store_id;

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public String getMeta_code() {
		return meta_code;
	}

	public void setMeta_code(String meta_code) {
		this.meta_code = meta_code;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getSub_type_id() {
		return sub_type_id;
	}

	public void setSub_type_id(String sub_type_id) {
		this.sub_type_id = sub_type_id;
	}

	public String getOut_code() {
		return out_code;
	}

	public void setOut_code(String out_code) {
		this.out_code = out_code;
	}

	public Integer getSource_nature_code() {
		return source_nature_code;
	}

	public void setSource_nature_code(Integer source_nature_code) {
		this.source_nature_code = source_nature_code;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

}
