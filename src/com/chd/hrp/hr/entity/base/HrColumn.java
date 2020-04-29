package com.chd.hrp.hr.entity.base;

import java.io.Serializable;

public class HrColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer group_id;

	/**
	 * 
	 */
	private Integer hos_id;

	/**
	 * 
	 */
	private String copy_code;

	/**
	 * 归属表
	 */
	private String tab_code;

	/**
	 * 列名
	 */
	private String col_code;

	/**
	 * 列描述
	 */
	private String col_name;

	/**
	 * 数据类型
	 */
	private String data_type;

	/**
	 * 字段长度
	 */
	private Integer filed_length;

	/**
	 * 精度
	 */
	private Integer prec;

	/**
	 * 字段长度类型 0:filedname filedtype,1:filedname filedtype(length),2:filedname
	 * filedtype(length,prec)
	 */
	private Integer fileTypeLength;

	/**
	 * 是否主键
	 */
	private Integer is_pk;

	/**
	 * 是否必填
	 */
	private Integer is_m;

	/**
	 * 是否内置
	 */
	private Integer is_innr;

	/**
	 * 代码表
	 */
	private String field_tab_code;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 排序（升序）
	 */
	private Integer sort;

	/**
	 * 
	 */
	private String store_type_code;

	/**
	 * 
	 */
	private String col_name_show;

	/**
	 * 
	 */
	private String com_type_code;

	/**
	 * 
	 */
	private Integer seq_no;

	/**
	 * 
	 */
	private Integer col_width;

	/**
	 * 
	 */
	private Integer is_view;

	/**
	 * 
	 */
	private Integer is_view_tab;

	/**
	 * left:左对齐 right:右对齐 center:居中对齐
	 */
	private String text_align;

	/**
	 * 最小为1(占一列)，最大为4（占一行）
	 * 
	 */
	private Integer field_width;

	/**
	 * 
	 */
	private Integer is_verify;

	/**
	 * 
	 */
	private Integer is_auto;

	/**
	 * 
	 */
	private Integer is_read;

	/**
	 * 
	 */
	private Integer is_default;

	/**
	 * 
	 */
	private String default_value;

	/**
	 * 
	 */
	private String default_text;

	/**
	 * 
	 */
	private Integer is_change;

	/**
	 * 
	 */
	private String change_col_code;

	/**
	 * 
	 */
	private Integer is_cite;

	/**
	 * 
	 */
	private String con_sign_code;
	
	/**
	 * 
	 */
	private Integer is_section;

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getTab_code() {
		return tab_code;
	}

	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
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

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public Integer getFiled_length() {
		return filed_length;
	}

	public void setFiled_length(Integer filed_length) {
		this.filed_length = filed_length;
	}

	public Integer getPrec() {
		return prec;
	}

	public void setPrec(Integer prec) {
		this.prec = prec;
	}

	public Integer getFileTypeLength() {
		return fileTypeLength;
	}

	public void setFileTypeLength(Integer fileTypeLength) {
		this.fileTypeLength = fileTypeLength;
	}

	public Integer getIs_pk() {
		return is_pk;
	}

	public void setIs_pk(Integer is_pk) {
		this.is_pk = is_pk;
	}

	public Integer getIs_m() {
		return is_m;
	}

	public void setIs_m(Integer is_m) {
		this.is_m = is_m;
	}

	public Integer getIs_innr() {
		return is_innr;
	}

	public void setIs_innr(Integer is_innr) {
		this.is_innr = is_innr;
	}

	public String getField_tab_code() {
		return field_tab_code;
	}

	public void setField_tab_code(String field_tab_code) {
		this.field_tab_code = field_tab_code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStore_type_code() {
		return store_type_code;
	}

	public void setStore_type_code(String store_type_code) {
		this.store_type_code = store_type_code;
	}

	public String getCol_name_show() {
		return col_name_show;
	}

	public void setCol_name_show(String col_name_show) {
		this.col_name_show = col_name_show;
	}

	public String getCom_type_code() {
		return com_type_code;
	}

	public void setCom_type_code(String com_type_code) {
		this.com_type_code = com_type_code;
	}

	public Integer getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}

	public Integer getCol_width() {
		return col_width;
	}

	public void setCol_width(Integer col_width) {
		this.col_width = col_width;
	}

	public Integer getIs_view() {
		return is_view;
	}

	public void setIs_view(Integer is_view) {
		this.is_view = is_view;
	}

	public Integer getIs_view_tab() {
		return is_view_tab;
	}

	public void setIs_view_tab(Integer is_view_tab) {
		this.is_view_tab = is_view_tab;
	}

	public String getText_align() {
		return text_align;
	}

	public void setText_align(String text_align) {
		this.text_align = text_align;
	}

	public Integer getField_width() {
		return field_width;
	}

	public void setField_width(Integer field_width) {
		this.field_width = field_width;
	}

	public Integer getIs_verify() {
		return is_verify;
	}

	public void setIs_verify(Integer is_verify) {
		this.is_verify = is_verify;
	}

	public Integer getIs_auto() {
		return is_auto;
	}

	public void setIs_auto(Integer is_auto) {
		this.is_auto = is_auto;
	}

	public Integer getIs_read() {
		return is_read;
	}

	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}

	public Integer getIs_default() {
		return is_default;
	}

	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getDefault_text() {
		return default_text;
	}

	public void setDefault_text(String default_text) {
		this.default_text = default_text;
	}

	public Integer getIs_change() {
		return is_change;
	}

	public void setIs_change(Integer is_change) {
		this.is_change = is_change;
	}

	public String getChange_col_code() {
		return change_col_code;
	}

	public void setChange_col_code(String change_col_code) {
		this.change_col_code = change_col_code;
	}

	public Integer getIs_cite() {
		return is_cite;
	}

	public void setIs_cite(Integer is_cite) {
		this.is_cite = is_cite;
	}

	public String getCon_sign_code() {
		return con_sign_code;
	}

	public void setCon_sign_code(String con_sign_code) {
		this.con_sign_code = con_sign_code;
	}

	public Integer getIs_section() {
		return is_section;
	}

	public void setIs_section(Integer is_section) {
		this.is_section = is_section;
	}
	

}
