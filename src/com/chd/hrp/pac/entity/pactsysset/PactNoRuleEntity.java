package com.chd.hrp.pac.entity.pactsysset;

import java.io.Serializable;

public class PactNoRuleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7044693400235728612L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String table_code;
	private String table_name;
	private String prefixe;
	private Integer is_type;
	private Integer is_year;
	private Integer is_month;
	private Integer seq_no;
	private String code_colume;

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

	public String getTable_code() {
		return table_code;
	}

	public void setTable_code(String table_code) {
		this.table_code = table_code;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getPrefixe() {
		return prefixe;
	}

	public void setPrefix(String prefixe) {
		this.prefixe = prefixe;
	}

	public Integer getIs_type() {
		return is_type;
	}

	public void setIs_type(Integer is_type) {
		this.is_type = is_type;
	}

	public Integer getIs_year() {
		return is_year;
	}

	public void setIs_year(Integer is_year) {
		this.is_year = is_year;
	}

	public Integer getIs_month() {
		return is_month;
	}

	public void setIs_month(Integer is_month) {
		this.is_month = is_month;
	}

	public Integer getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}

	public String getCode_colume() {
		return code_colume;
	}

	public void setCode_colume(String code_colume) {
		this.code_colume = code_colume;
	}

}
