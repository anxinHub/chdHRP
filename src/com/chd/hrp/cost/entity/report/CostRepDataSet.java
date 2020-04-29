package com.chd.hrp.cost.entity.report;

public class CostRepDataSet {

	private Long group_id;
	private Long hos_id;
	private String mod_code;
	private String ds_code;
	private String ds_name;
	private Integer state;
	private Integer ds_type;
	private String sqlcontent;
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
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getDs_code() {
		return ds_code;
	}
	public void setDs_code(String ds_code) {
		this.ds_code = ds_code;
	}
	public String getDs_name() {
		return ds_name;
	}
	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getDs_type() {
		return ds_type;
	}
	public void setDs_type(Integer ds_type) {
		this.ds_type = ds_type;
	}
	public String getSqlcontent() {
		return sqlcontent;
	}
	public void setSqlcontent(String sqlcontent) {
		this.sqlcontent = sqlcontent;
	}
}
