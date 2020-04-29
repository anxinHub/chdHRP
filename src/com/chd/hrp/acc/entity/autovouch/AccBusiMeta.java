package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class AccBusiMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String meta_code;

	private String meta_name;

	private String type_table;

	private Integer is_auto;

	private String where_sql;

	private Integer is_inout_type;

	private Integer is_resource;

	private Integer is_store;

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

	public String getMeta_code() {
		return meta_code;
	}

	public void setMeta_code(String meta_code) {
		this.meta_code = meta_code;
	}

	public String getMeta_name() {
		return meta_name;
	}

	public void setMeta_name(String meta_name) {
		this.meta_name = meta_name;
	}

	public String getType_table() {
		return type_table;
	}

	public void setType_table(String type_table) {
		this.type_table = type_table;
	}

	public Integer getIs_auto() {
		return is_auto;
	}

	public void setIs_auto(Integer is_auto) {
		this.is_auto = is_auto;
	}

	public String getWhere_sql() {
		return where_sql;
	}

	public void setWhere_sql(String where_sql) {
		this.where_sql = where_sql;
	}

	public Integer getIs_inout_type() {
		return is_inout_type;
	}

	public void setIs_inout_type(Integer is_inout_type) {
		this.is_inout_type = is_inout_type;
	}

	public Integer getIs_resource() {
		return is_resource;
	}

	public void setIs_resource(Integer is_resource) {
		this.is_resource = is_resource;
	}

	public Integer getIs_store() {
		return is_store;
	}

	public void setIs_store(Integer is_store) {
		this.is_store = is_store;
	}
	
	
	
}
