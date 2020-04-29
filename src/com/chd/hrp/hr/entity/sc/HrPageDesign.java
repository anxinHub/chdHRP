package com.chd.hrp.hr.entity.sc;

public class HrPageDesign {

	private static final long serialVersionUID = 7945746194962331406L;
	
	private long group_id;
	
	private long hos_id;
	
	private String template_code;
	
	private String table_type_code;
	
	private String table_type_name;
	
	private String page_code;
	
	private String page_name;
	
	private int is_stop;
	
	private int is_innr;
	
	private String page_json;
	
	private int page_sort;
	
	private String note;

	public long getGroup_id() {
		return group_id;
	}

	public long getHos_id() {
		return hos_id;
	}

	public String getTemplate_code() {
		return template_code;
	}

	public String getTable_type_code() {
		return table_type_code;
	}

	public String getTable_type_name() {
		return table_type_name;
	}

	public String getPage_code() {
		return page_code;
	}

	public String getPage_name() {
		return page_name;
	}

	public int getIs_stop() {
		return is_stop;
	}

	public int getIs_innr() {
		return is_innr;
	}

	public String getPage_json() {
		return page_json;
	}

	public int getPage_sort() {
		return page_sort;
	}

	public String getNote() {
		return note;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public void setTable_type_code(String table_type_code) {
		this.table_type_code = table_type_code;
	}

	public void setTable_type_name(String table_type_name) {
		this.table_type_name = table_type_name;
	}

	public void setPage_code(String page_code) {
		this.page_code = page_code;
	}

	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}

	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}

	public void setIs_innr(int is_innr) {
		this.is_innr = is_innr;
	}

	public void setPage_json(String page_json) {
		this.page_json = page_json;
	}

	public void setPage_sort(int page_sort) {
		this.page_sort = page_sort;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
