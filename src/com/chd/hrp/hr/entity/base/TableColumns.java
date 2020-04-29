package com.chd.hrp.hr.entity.base;

public class TableColumns {

	private String table_name;
	private String column_name;
	private String data_type;
	private Integer data_length;
	private Integer data_precision;
	private Integer data_scale;
	private String nullable;
	private Integer column_id;
	private Integer default_length;
	private String data_default;
	/**
	 * 主键:P
	 */
	private String column_key;

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public Integer getData_length() {
		return data_length;
	}

	public void setData_length(Integer data_length) {
		this.data_length = data_length;
	}

	public Integer getData_precision() {
		return data_precision;
	}

	public void setData_precision(Integer data_precision) {
		this.data_precision = data_precision;
	}

	public Integer getData_scale() {
		return data_scale;
	}

	public void setData_scale(Integer data_scale) {
		this.data_scale = data_scale;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public Integer getColumn_id() {
		return column_id;
	}

	public void setColumn_id(Integer column_id) {
		this.column_id = column_id;
	}

	public Integer getDefault_length() {
		return default_length;
	}

	public void setDefault_length(Integer default_length) {
		this.default_length = default_length;
	}

	public String getData_default() {
		return data_default;
	}

	public void setData_default(String data_default) {
		this.data_default = data_default;
	}

	public String getColumn_key() {
		return column_key;
	}

	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}

}
