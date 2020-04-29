package com.chd.hrp.acc.entity.autovouch;

import java.io.Serializable;

public class SysBusiTable implements Serializable {

	private static final long serialVersionUID = 1L;

	private String table_id;

	private String table_name;

	private String id_field;

	private String no_field;

	private String code_field;

	private String name_field;

	private String level_field;

	private String year_field;
	
	private String where_sql;

	private Integer table_level;

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getId_field() {
		return id_field;
	}

	public void setId_field(String id_field) {
		this.id_field = id_field;
	}

	public String getNo_field() {
		return no_field;
	}

	public void setNo_field(String no_field) {
		this.no_field = no_field;
	}

	public String getCode_field() {
		return code_field;
	}

	public void setCode_field(String code_field) {
		this.code_field = code_field;
	}

	public String getName_field() {
		return name_field;
	}

	public void setName_field(String name_field) {
		this.name_field = name_field;
	}

	public String getLevel_field() {
		return level_field;
	}

	public void setLevel_field(String level_field) {
		this.level_field = level_field;
	}

	public String getYear_field() {
		return year_field;
	}

	public void setYear_field(String year_field) {
		this.year_field = year_field;
	}

	public Integer getTable_level() {
		return table_level;
	}

	public void setTable_level(Integer table_level) {
		this.table_level = table_level;
	}

	public String getWhere_sql() {
		return where_sql;
	}

	public void setWhere_sql(String where_sql) {
		this.where_sql = where_sql;
	}

}
