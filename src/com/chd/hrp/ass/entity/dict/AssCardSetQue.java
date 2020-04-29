package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;

public class AssCardSetQue implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private Integer group_id;     
	private Integer hos_id;      
	private String copy_code;    
	private String ass_naturs;   
	private String naturs_name;
	private String table_name;   
	private String col_code;    
	private String col_name;
	private String type_code;    
	private Integer seq_no;       
	private Integer is_view;      
	private String re_url;       
	private Integer is_change;    
	private Integer field_width;  
	private Integer is_section;   
	private Integer is_init;      
	private Integer is_verify;    
	private Integer is_default;   
	private String default_value;
	private String default_text;
	
	
	public String getDefault_text() {
		return default_text;
	}
	public void setDefault_text(String default_text) {
		this.default_text = default_text;
	}
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public String getNaturs_name() {
		return naturs_name;
	}
	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}
	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}
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
	public String getAss_naturs() {
		return ass_naturs;
	}
	public void setAss_naturs(String ass_naturs) {
		this.ass_naturs = ass_naturs;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getCol_code() {
		return col_code;
	}
	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public Integer getSeq_no() {
		return seq_no;
	}
	public void setSet_no(Integer seq_no) {
		this.seq_no = seq_no;
	}
	public Integer getIs_view() {
		return is_view;
	}
	public void setIs_view(Integer is_view) {
		this.is_view = is_view;
	}
	public String getRe_url() {
		return re_url;
	}
	public void setRe_url(String re_url) {
		this.re_url = re_url;
	}
	public Integer getIs_change() {
		return is_change;
	}
	public void setIs_change(Integer is_change) {
		this.is_change = is_change;
	}
	public Integer getField_width() {
		return field_width;
	}
	public void setField_width(Integer field_width) {
		this.field_width = field_width;
	}
	public Integer getIs_section() {
		return is_section;
	}
	public void setIs_section(Integer is_section) {
		this.is_section = is_section;
	}
	public Integer getIs_init() {
		return is_init;
	}
	public void setIs_init(Integer is_init) {
		this.is_init = is_init;
	}
	public Integer getIs_verify() {
		return is_verify;
	}
	public void setIs_verify(Integer is_verify) {
		this.is_verify = is_verify;
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

}
