package com.chd.hrp.acc.entity;
/**
 * 工资方案与职工分类关系对应
 * @author yang
 *
 */
public class AccWageSKind {

	private Integer group_id;
	private Integer hos_id;
	private Long scheme_id;
	private String scheme_name;
	private String kind_code;
	private String kind_name;
	
	public AccWageSKind(){}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public Long getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(Long scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getScheme_name() {
		return scheme_name;
	}

	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
}
