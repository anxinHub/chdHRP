package com.chd.hrp.hpm.entity;

import java.io.Serializable;

public class AphiTemplateData implements Serializable {

	/**
	 * 数据采集模板配置
	 * */
	private static final long serialVersionUID = 1L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String template_code;

	private String template_name;

	private String template_note;

	private String template_table;

	private String template_detail_code;

	private String group_view;

	private String columns_type;

	private String columns_view;

	private String columns_table;

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

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_note() {
		return template_note;
	}

	public void setTemplate_note(String template_note) {
		this.template_note = template_note;
	}

	public String getTemplate_table() {
		return template_table;
	}

	public void setTemplate_table(String template_table) {
		this.template_table = template_table;
	}

	public String getTemplate_detail_code() {
		return template_detail_code;
	}

	public void setTemplate_detail_code(String template_detail_code) {
		this.template_detail_code = template_detail_code;
	}

	public String getGroup_view() {
		return group_view;
	}

	public void setGroup_view(String group_view) {
		this.group_view = group_view;
	}

	public String getColumns_type() {
		return columns_type;
	}

	public void setColumns_type(String columns_type) {
		this.columns_type = columns_type;
	}

	public String getColumns_view() {
		return columns_view;
	}

	public void setColumns_view(String columns_view) {
		this.columns_view = columns_view;
	}

	public String getColumns_table() {
		return columns_table;
	}

	public void setColumns_table(String columns_table) {
		this.columns_table = columns_table;
	}

}
