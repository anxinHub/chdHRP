package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableColumn implements Serializable, Comparable<HrTableColumn> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7945746194962331406L;

	private String tab_code;
	private String tab_name;
	private String col_code;
	private String col_name;
	private String data_type_code;
	private String data_type_name;
	private String filed_length;
	private String value_mode_code;
	private String value_mode_name;
	private String field_tab_code;
	private String field_tab_name;
	private Integer is_innr;
	private String is_innr_text;
	private Integer is_unique;
	private String is_unique_text;
	private Integer is_pk;
	private String is_pk_text;
	private Integer sort;
	private String note;

	// 用于查询设计器函数回显
	private String func;
	private String func_text;
	private String param;

	// 用于代码表外部引用 代码项对应数据表回显
	private String codeItem;
	private String colCode;
	private String colName;

	private String fileTypeLength;
	private String status; //状态（ligerUi 行状态 add，update）

	public String getTab_code() {
		return tab_code;
	}

	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}

	public String getTab_name() {
		return tab_name;
	}

	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}

	public String getCol_code() {
		return col_code;
	}

	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}

	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	public String getData_type_code() {
		return data_type_code;
	}

	public void setData_type_code(String data_type_code) {
		this.data_type_code = data_type_code;
	}

	public String getData_type_name() {
		return data_type_name;
	}

	public void setData_type_name(String data_type_name) {
		this.data_type_name = data_type_name;
	}

	public String getFiled_length() {
		return filed_length;
	}

	public void setFiled_length(String filed_length) {
		this.filed_length = filed_length;
	}

	public String getValue_mode_code() {
		return value_mode_code;
	}

	public void setValue_mode_code(String value_mode_code) {
		this.value_mode_code = value_mode_code;
	}

	public String getValue_mode_name() {
		return value_mode_name;
	}

	public void setValue_mode_name(String value_mode_name) {
		this.value_mode_name = value_mode_name;
	}

	public String getField_tab_code() {
		return field_tab_code;
	}

	public void setField_tab_code(String field_tab_code) {
		this.field_tab_code = field_tab_code;
	}

	public String getField_tab_name() {
		return field_tab_name;
	}

	public void setField_tab_name(String field_tab_name) {
		this.field_tab_name = field_tab_name;
	}

	public Integer getIs_innr() {
		return is_innr;
	}

	public void setIs_innr(Integer is_innr) {
		this.is_innr = is_innr;
	}

	public String getIs_innr_text() {
		return is_innr_text;
	}

	public void setIs_innr_text(String is_innr_text) {
		this.is_innr_text = is_innr_text;
	}

	public Integer getIs_unique() {
		return is_unique;
	}

	public void setIs_unique(Integer is_unique) {
		this.is_unique = is_unique;
	}

	public String getIs_unique_text() {
		return is_unique_text;
	}

	public void setIs_unique_text(String is_unique_text) {
		this.is_unique_text = is_unique_text;
	}

	public Integer getIs_pk() {
		return is_pk;
	}

	public void setIs_pk(Integer is_pk) {
		this.is_pk = is_pk;
	}

	public String getIs_pk_text() {
		return is_pk_text;
	}

	public void setIs_pk_text(String is_pk_text) {
		this.is_pk_text = is_pk_text;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getFunc_text() {
		return func_text;
	}

	public void setFunc_text(String func_text) {
		this.func_text = func_text;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getCodeItem() {
		return codeItem;
	}

	public void setCodeItem(String codeItem) {
		this.codeItem = codeItem;
	}

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getFileTypeLength() {
		return fileTypeLength;
	}

	public void setFileTypeLength(String fileTypeLength) {
		this.fileTypeLength = fileTypeLength;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(HrTableColumn o) {
		if (this.sort < o.getSort())
			return -1;
		if (this.sort > o.getSort())
			return 1;
		return 0;
	}

}
