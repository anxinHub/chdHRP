package com.chd.hrp.hr.entity.sc;

public class HrPageTemplate {

private static final long serialVersionUID = 7945746194962331406L;
	
	private String  template_code; //模板编码
	private String  template_name;  //模板名称
	private String  template_type;  //模板类型
	private String  template_img;  //模板缩略图
	private String  template_path;  //模板路径
	private int     template_sort;  //模板顺序
	private String  template_json;  //模板JSON
	private String  template_note;  //模板说明
	public String getTemplate_code() {
		return template_code;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public String getTemplate_type() {
		return template_type;
	}
	public String getTemplate_img() {
		return template_img;
	}
	public String getTemplate_path() {
		return template_path;
	}
	public int getTemplate_sort() {
		return template_sort;
	}
	public String getTemplate_json() {
		return template_json;
	}
	public String getTemplate_note() {
		return template_note;
	}
	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	public void setTemplate_img(String template_img) {
		this.template_img = template_img;
	}
	public void setTemplate_path(String template_path) {
		this.template_path = template_path;
	}
	public void setTemplate_sort(int template_sort) {
		this.template_sort = template_sort;
	}
	public void setTemplate_json(String template_json) {
		this.template_json = template_json;
	}
	public void setTemplate_note(String template_note) {
		this.template_note = template_note;
	}
}
