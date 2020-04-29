package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableColumnGridConfig implements Serializable, Comparable<HrTableColumnGridConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7924616245198320278L;

	private String tab_code;
	private String tab_name;
	private String col_code;
	private String col_name;
	private String view_name;
	private String com_type_code;
	private String com_type_name;
	private String width;
	private Integer is_view;
	private String text_align;
	private String text_align_text;
	private Integer sort;
	private String value_mode_code;
	private String field_tab_code;
	private Integer status;//构结构是否修改状态

	public HrTableColumnGridConfig() {
	}

	private HrTableColumnGridConfig(Builder builder) {
		this.tab_code = builder.tab_code;
		this.tab_name = builder.tab_name;
		this.col_code = builder.col_code;
		this.col_name = builder.col_name;
		this.view_name = builder.view_name;
		this.com_type_code = builder.com_type_code;
		this.com_type_name = builder.com_type_name;
		this.width = builder.width;
		this.is_view = builder.is_view;
		this.text_align = builder.text_align;
		this.text_align_text = builder.text_align_text;
		this.sort = builder.sort;
		this.value_mode_code = builder.value_mode_code;
		this.field_tab_code = builder.field_tab_code;
		this.status = builder.status;
	}

	public static class Builder {
		private String tab_code;
		private String tab_name;
		private String col_code;
		private String col_name;
		private String view_name;
		private String com_type_code = "02";
		private String com_type_name = "文本框";
		private String width = "120";
		private Integer is_view = 1;
		private String text_align = "left";
		private String text_align_text = "左对齐";
		private Integer sort = 0;
		private String value_mode_code;
		private String field_tab_code;
		private Integer status;

		public Builder(String tab_code, String tab_name, String col_code, String col_name) {
			this.tab_code = tab_code;
			this.tab_name = tab_name;
			this.col_code = col_code;
			this.col_name = col_name;
			this.view_name = col_name;
		}
		
		public Builder component(String com_type_code, String com_type_name){
			this.com_type_code = com_type_code;
			this.com_type_name = com_type_name;
			return this;
		}
		
		public Builder valueMode(String com_type_code, String com_type_name){
			this.value_mode_code = com_type_code;
			this.field_tab_code = com_type_name;
			return this;
		}
		
		public Builder sort(int sort){
			this.sort = sort;
			return this;
		}
		
		public Builder status(int status){
			this.status = status;
			return this;
		}

		public HrTableColumnGridConfig build() {
			return new HrTableColumnGridConfig(this);
		}

	}

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

	public String getView_name() {
		return view_name;
	}

	public void setView_name(String view_name) {
		this.view_name = view_name;
	}

	public String getCom_type_code() {
		return com_type_code;
	}

	public void setCom_type_code(String com_type_code) {
		this.com_type_code = com_type_code;
	}

	public String getCom_type_name() {
		return com_type_name;
	}

	public void setCom_type_name(String com_type_name) {
		this.com_type_name = com_type_name;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Integer getIs_view() {
		return is_view;
	}

	public void setIs_view(Integer is_view) {
		this.is_view = is_view;
	}

	public String getText_align() {
		return text_align;
	}

	public void setText_align(String text_align) {
		this.text_align = text_align;
	}

	public String getText_align_text() {
		return text_align_text;
	}

	public void setText_align_text(String text_align_text) {
		this.text_align_text = text_align_text;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getValue_mode_code() {
		return value_mode_code;
	}

	public void setValue_mode_code(String value_mode_code) {
		this.value_mode_code = value_mode_code;
	}

	public String getField_tab_code() {
		return field_tab_code;
	}

	public void setField_tab_code(String field_tab_code) {
		this.field_tab_code = field_tab_code;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int compareTo(HrTableColumnGridConfig o) {
		if (this.sort < o.getSort())
			return -1;
		if (this.sort > o.getSort())
			return 1;
		return 0;
	}

}
