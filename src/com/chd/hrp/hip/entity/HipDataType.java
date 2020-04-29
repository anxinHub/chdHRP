package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipDataType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 58664509342467786L;

	private Long type_id;
	private Long group_id;
	private Long hos_id;
	private Long dgroup_id;
	private String dgroup_name;
	private Long dhos_id;
	private String dhos_name;
	private String dcopy_code;
	private String dcopy_name;
	private String mod_code;
	private String type_code;
	private String type_name;
	private String source_code;
	private String source_name;
	private String to_table;
	private String q_sql;
	private int is_stop;
	private String note;
	private int data_type;
	private int sync_type;
	private String pk_col;
	private int is_group;
	private int is_hos;
	private int is_copy;
	
	
	
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getDgroup_name() {
		return dgroup_name;
	}
	public void setDgroup_name(String dgroup_name) {
		this.dgroup_name = dgroup_name;
	}
	public String getDhos_name() {
		return dhos_name;
	}
	public void setDhos_name(String dhos_name) {
		this.dhos_name = dhos_name;
	}
	public String getDcopy_name() {
		return dcopy_name;
	}
	public void setDcopy_name(String dcopy_name) {
		this.dcopy_name = dcopy_name;
	}
	public int getIs_group() {
		return is_group;
	}
	public void setIs_group(int is_group) {
		this.is_group = is_group;
	}
	public int getIs_hos() {
		return is_hos;
	}
	public void setIs_hos(int is_hos) {
		this.is_hos = is_hos;
	}
	public int getIs_copy() {
		return is_copy;
	}
	public void setIs_copy(int is_copy) {
		this.is_copy = is_copy;
	}
	public Long getType_id() {
		return type_id;
	}
	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getQ_sql() {
		return q_sql;
	}
	public void setQ_sql(String q_sql) {
		this.q_sql = q_sql;
	}
	public int getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
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
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getSource_code() {
		return source_code;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	public String getTo_table() {
		return to_table;
	}
	public void setTo_table(String to_table) {
		this.to_table = to_table;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getDgroup_id() {
		return dgroup_id;
	}
	public void setDgroup_id(Long dgroup_id) {
		this.dgroup_id = dgroup_id;
	}
	public Long getDhos_id() {
		return dhos_id;
	}
	public void setDhos_id(Long dhos_id) {
		this.dhos_id = dhos_id;
	}
	public String getDcopy_code() {
		return dcopy_code;
	}
	public void setDcopy_code(String dcopy_code) {
		this.dcopy_code = dcopy_code;
	}
	public int getData_type() {
		return data_type;
	}
	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	public int getSync_type() {
		return sync_type;
	}
	public void setSync_type(int sync_type) {
		this.sync_type = sync_type;
	}
	public String getPk_col() {
		return pk_col;
	}
	public void setPk_col(String pk_col) {
		this.pk_col = pk_col;
	}
	
	
	
}
