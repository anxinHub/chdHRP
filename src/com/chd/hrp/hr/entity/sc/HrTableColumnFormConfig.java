package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

import com.chd.hrp.hr.entity.sc.HrTableColumnGridConfig.Builder;

public class HrTableColumnFormConfig implements Serializable, Comparable<HrTableColumnFormConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662467707063863987L;

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
	private Integer field_width;
	private String field_width_name;
	private Integer is_gen;
	private Integer is_required;
	private Integer is_default_value;
	private String default_value;
	private String default_value_name;
	private Integer sort;
	private String value_mode_code;
	private String field_tab_code;
	
	//用于查询Form
	private String con_sign_code;
	private String con_sign_name;
	private Integer is_section;
	
	private Integer status;//构结构是否修改状态

	public HrTableColumnFormConfig() {}
	
	private HrTableColumnFormConfig(Builder builder) {
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
		this.field_width = builder.field_width;
		this.field_width_name = builder.field_width_name;
		this.is_gen = builder.is_gen;
		this.is_required = builder.is_required;
		this.is_default_value = builder.is_default_value;
		this.default_value = builder.default_value;
		this.default_value_name = builder.default_value_name;
		this.sort = builder.sort;
		this.value_mode_code = builder.value_mode_code;
		this.field_tab_code = builder.field_tab_code;
		this.con_sign_code = builder.con_sign_code;
		this.con_sign_name = builder.con_sign_name;
		this.is_section = builder.is_section;
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
		private String width = "160";
		private Integer is_view = 1;
		private String text_align = "left";
		private String text_align_text = "左对齐";
		private Integer sort = 0;
		
		private Integer field_width = 1;//字段列数
		private String field_width_name = "1列";
		private Integer is_gen = 0;
		private Integer is_required = 0;
		private Integer is_default_value = 0;
		private String default_value = "";
		private String default_value_name = "";
		
		private String con_sign_code = "11";//查询方式
		private String con_sign_name = "等于";
		private Integer is_section = 0;//是否区间
		
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

		public HrTableColumnFormConfig build() {
			return new HrTableColumnFormConfig(this);
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

	public Integer getField_width() {
		return field_width;
	}

	public void setField_width(Integer field_width) {
		this.field_width = field_width;
	}

	public String getField_width_name() {
		return field_width_name;
	}

	public void setField_width_name(String field_width_name) {
		this.field_width_name = field_width_name;
	}

	public Integer getIs_gen() {
		return is_gen;
	}

	public void setIs_gen(Integer is_gen) {
		this.is_gen = is_gen;
	}

	public Integer getIs_required() {
		return is_required;
	}

	public void setIs_required(Integer is_required) {
		this.is_required = is_required;
	}

	public Integer getIs_default_value() {
		return is_default_value;
	}

	public void setIs_default_value(Integer is_default_value) {
		this.is_default_value = is_default_value;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getDefault_value_name() {
		return default_value_name;
	}

	public void setDefault_value_name(String default_value_name) {
		this.default_value_name = default_value_name;
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
	
	public String getCon_sign_code() {
		return con_sign_code;
	}

	public void setCon_sign_code(String con_sign_code) {
		this.con_sign_code = con_sign_code;
	}

	public String getCon_sign_name() {
		return con_sign_name;
	}

	public void setCon_sign_name(String con_sign_name) {
		this.con_sign_name = con_sign_name;
	}

	public Integer getIs_section() {
		return is_section;
	}

	public void setIs_section(Integer is_section) {
		this.is_section = is_section;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int compareTo(HrTableColumnFormConfig o) {
		if (this.sort < o.getSort())
			return -1;
		if (this.sort > o.getSort())
			return 1;
		return 0;
	}

}
