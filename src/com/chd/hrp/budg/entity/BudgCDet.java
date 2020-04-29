package com.chd.hrp.budg.entity;
//预算控制明细
public class BudgCDet {
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
	 * 预算年度
	 */
	private String budg_year;

	

	/**
	 * 方案编码
	 */
	private String plan_code;
	
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
	public String getBudg_year() {
		return budg_year;
	}
	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}
	public String getPlan_code() {
		return plan_code;
	}
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
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
	public String getLink_code() {
		return link_code;
	}
	public void setLink_code(String link_code) {
		this.link_code = link_code;
	}
	public String getLink_name() {
		return link_name;
	}
	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getCont_l() {
		return cont_l;
	}
	public void setCont_l(String cont_l) {
		this.cont_l = cont_l;
	}
	public String getL_name() {
		return l_name;
	}
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	public String getCont_p() {
		return cont_p;
	}
	public void setCont_p(String cont_p) {
		this.cont_p = cont_p;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getCont_w() {
		return cont_w;
	}
	public void setCont_w(String cont_w) {
		this.cont_w = cont_w;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	/**
	 * 模块编码
	 */
	private String mod_code;
	private String mod_name;
	/**
	 * 环节编码
	 */
	private String link_code;
	private String link_name;
	/**
	 * 预算表编码
	 */
	private String item_code;
	private String item_name;

	/**
	 * 控制层次
	 */
	private String cont_l;
	private String l_name;
	/**
	 * 控制期间
	 */
	private String cont_p;
	private String p_name;
	/**
	 * 控制方式
	 */
	private String cont_w;
	private String w_name;
	
	
	
	
	
	
}
