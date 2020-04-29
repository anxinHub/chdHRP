/*
 *
 */package com.chd.hrp.htcg.entity.making;

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

public class HtcgSchemePathConf implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private Integer mr_group;
	private Integer mr_sample;
	private Integer clp_step;
	private Integer recipe_p_merge;
	private Integer recipe_d_merge;
	private Integer recipe_p_group;
	private Integer recipe_d_group;
	
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
	public Integer getMr_group() {
		return mr_group;
	}
	public Integer getMr_sample() {
		return mr_sample;
	}
	public Integer getClp_step() {
		return clp_step;
	}
	public Integer getRecipe_p_merge() {
		return recipe_p_merge;
	}
	public Integer getRecipe_d_merge() {
		return recipe_d_merge;
	}
	public Integer getRecipe_p_group() {
		return recipe_p_group;
	}
	public Integer getRecipe_d_group() {
		return recipe_d_group;
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
	public void setMr_group(Integer mr_group) {
		this.mr_group = mr_group;
	}
	public void setMr_sample(Integer mr_sample) {
		this.mr_sample = mr_sample;
	}
	public void setClp_step(Integer clp_step) {
		this.clp_step = clp_step;
	}
	public void setRecipe_p_merge(Integer recipe_p_merge) {
		this.recipe_p_merge = recipe_p_merge;
	}
	public void setRecipe_d_merge(Integer recipe_d_merge) {
		this.recipe_d_merge = recipe_d_merge;
	}
	public void setRecipe_p_group(Integer recipe_p_group) {
		this.recipe_p_group = recipe_p_group;
	}
	public void setRecipe_d_group(Integer recipe_d_group) {
		this.recipe_d_group = recipe_d_group;
	}
	  
}