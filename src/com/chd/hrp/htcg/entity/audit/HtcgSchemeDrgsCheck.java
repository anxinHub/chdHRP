package com.chd.hrp.htcg.entity.audit;

import java.io.Serializable;

public class HtcgSchemeDrgsCheck implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private String drgs_code;
	private String drgs_name;
	private String icd_rule_code;
	private String icd_rule_name;
	private Integer drgs_day;
	private String mr_rule_code;
	private String mr_rule_name;
	private String recipe_merge_code;
	private String recipe_merge_name;
	private String recipe_group_code;
	private String recipe_group_name;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getDrgs_code() {
		return drgs_code;
	}
	public String getDrgs_name() {
		return drgs_name;
	}
	public String getIcd_rule_code() {
		return icd_rule_code;
	}
	public String getIcd_rule_name() {
		return icd_rule_name;
	}
	public Integer getDrgs_day() {
		return drgs_day;
	}
	public String getMr_rule_code() {
		return mr_rule_code;
	}
	public String getMr_rule_name() {
		return mr_rule_name;
	}
	public String getRecipe_merge_code() {
		return recipe_merge_code;
	}
	public String getRecipe_merge_name() {
		return recipe_merge_name;
	}
	public String getRecipe_group_code() {
		return recipe_group_code;
	}
	public String getRecipe_group_name() {
		return recipe_group_name;
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
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setDrgs_code(String drgs_code) {
		this.drgs_code = drgs_code;
	}
	public void setDrgs_name(String drgs_name) {
		this.drgs_name = drgs_name;
	}
	public void setIcd_rule_code(String icd_rule_code) {
		this.icd_rule_code = icd_rule_code;
	}
	public void setIcd_rule_name(String icd_rule_name) {
		this.icd_rule_name = icd_rule_name;
	}
	public void setDrgs_day(Integer drgs_day) {
		this.drgs_day = drgs_day;
	}
	public void setMr_rule_code(String mr_rule_code) {
		this.mr_rule_code = mr_rule_code;
	}
	public void setMr_rule_name(String mr_rule_name) {
		this.mr_rule_name = mr_rule_name;
	}
	public void setRecipe_merge_code(String recipe_merge_code) {
		this.recipe_merge_code = recipe_merge_code;
	}
	public void setRecipe_merge_name(String recipe_merge_name) {
		this.recipe_merge_name = recipe_merge_name;
	}
	public void setRecipe_group_code(String recipe_group_code) {
		this.recipe_group_code = recipe_group_code;
	}
	public void setRecipe_group_name(String recipe_group_name) {
		this.recipe_group_name = recipe_group_name;
	}
	
}
