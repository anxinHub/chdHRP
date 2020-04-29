/*
 *
 */package com.chd.hrp.htcg.entity.info;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcgDrugDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String drug_code;
	private String drug_name;
	private String drug_type_code;
	private String drug_type_name;
	private String mode_code;
	private String unit_code;
	private Double price;
	private Long fac_id;
	private Long fac_no;
	private String fac_code;
	private String fac_name;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getDrug_code() {
		return drug_code;
	}
	public String getDrug_name() {
		return drug_name;
	}
	public String getDrug_type_code() {
		return drug_type_code;
	}
	public String getDrug_type_name() {
		return drug_type_name;
	}
	public String getMode_code() {
		return mode_code;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public Double getPrice() {
		return price;
	}
	public Long getFac_id() {
		return fac_id;
	}
	public Long getFac_no() {
		return fac_no;
	}
	public String getFac_code() {
		return fac_code;
	}
	public String getFac_name() {
		return fac_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setDrug_code(String drug_code) {
		this.drug_code = drug_code;
	}
	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}
	public void setDrug_type_code(String drug_type_code) {
		this.drug_type_code = drug_type_code;
	}
	public void setDrug_type_name(String drug_type_name) {
		this.drug_type_name = drug_type_name;
	}
	public void setMode_code(String mode_code) {
		this.mode_code = mode_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
	}
	public void setFac_no(Long fac_no) {
		this.fac_no = fac_no;
	}
	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	 
	
	
}