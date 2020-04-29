package com.chd.hrp.htcg.entity.making;

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
public class HtcgSchemeRecipeGroupRule implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private String recipe_group_code;
	private String recipe_group_name;
	private double total_percent;
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
	public String getRecipe_group_code() {
		return recipe_group_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getRecipe_group_name() {
		return recipe_group_name;
	}
	public double getTotal_percent() {
		return total_percent;
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
	public void setRecipe_group_code(String recipe_group_code) {
		this.recipe_group_code = recipe_group_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setRecipe_group_name(String recipe_group_name) {
		this.recipe_group_name = recipe_group_name;
	}
	public void setTotal_percent(double total_percent) {
		this.total_percent = total_percent;
	}
}