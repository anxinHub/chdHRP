package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class DataDecode  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long decode_id;
	private Long type_id;
	private String table_col;
	private String source_col;
	private String decode_table;
	private String decode_col;
	private Integer decode_type;
	private String rela_col;
	private String create_type;
	private String join_table;
	private String el;
	private String note;
	private int is_group;
	private int is_hos;
	private int is_copy;
	private String query_sql;
	
	public Long getDecode_id() {
		return decode_id;
	}
	public void setDecode_id(Long decode_id) {
		this.decode_id = decode_id;
	}
	public Long getType_id() {
		return type_id;
	}
	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}
	public String getTable_col() {
		return table_col;
	}
	public void setTable_col(String table_col) {
		this.table_col = table_col;
	}
	public String getSource_col() {
		return source_col;
	}
	public void setSource_col(String source_col) {
		this.source_col = source_col;
	}
	public String getDecode_table() {
		return decode_table;
	}
	public void setDecode_table(String decode_table) {
		this.decode_table = decode_table;
	}
	public Integer getDecode_type() {
		return decode_type;
	}
	public void setDecode_type(Integer decode_type) {
		this.decode_type = decode_type;
	}
	public String getRela_col() {
		return rela_col;
	}
	public void setRela_col(String rela_col) {
		this.rela_col = rela_col;
	}
	public String getCreate_type() {
		return create_type;
	}
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}
	public String getJoin_table() {
		return join_table;
	}
	public void setJoin_table(String join_table) {
		this.join_table = join_table;
	}
	public String getEl() {
		return el;
	}
	public void setEl(String el) {
		this.el = el;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getIs_group() {
		return is_group;
	}
	public void setIs_group(int is_group) {
		this.is_group = is_group;
	}
	public int getIs_hos() {
		return is_hos;
	}
	public void setIs_hos(int is_hos) {
		this.is_hos = is_hos;
	}
	public int getIs_copy() {
		return is_copy;
	}
	public void setIs_copy(int is_copy) {
		this.is_copy = is_copy;
	}
	public String getDecode_col() {
		return decode_col;
	}
	public void setDecode_col(String decode_col) {
		this.decode_col = decode_col;
	}
	public String getQuery_sql() {
		return query_sql;
	}
	public void setQuery_sql(String query_sql) {
		this.query_sql = query_sql;
	}
	
}
