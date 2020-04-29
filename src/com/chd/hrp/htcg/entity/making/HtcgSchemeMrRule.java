package com.chd.hrp.htcg.entity.making;

import java.io.Serializable;
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

public class HtcgSchemeMrRule implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private String drgs_code;
	private String drgs_name;
	private String mr_rule_code;
	private String mr_rule_name;
	private Integer mr_rule_count;
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
	public String getMr_rule_code() {
		return mr_rule_code;
	}
	public String getMr_rule_name() {
		return mr_rule_name;
	}
	public Integer getMr_rule_count() {
		return mr_rule_count;
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
	public void setMr_rule_code(String mr_rule_code) {
		this.mr_rule_code = mr_rule_code;
	}
	public void setMr_rule_name(String mr_rule_name) {
		this.mr_rule_name = mr_rule_name;
	}
	public void setMr_rule_count(Integer mr_rule_count) {
		this.mr_rule_count = mr_rule_count;
	}
}