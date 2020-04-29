package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipDblinkSource implements Serializable {

	private static final long serialVersionUID = 1L;

	//dblink信息同步名称
	private String dblink_sync_name;
	
	//dblink链接
	private String dblink_code;
	
	//dblink链接中要使用的表
	private String dblink_table_code;
	
	//dblink链接中要使用的表名
	private String dblink_table_name;
	
	//dblink链接中要导入的字段（每个字段之间用英文','逗号隔开）
	private String dblink_columns;

	//hip中接收导入的表
	private String hip_table_code;
	
	//hip中接收导入的表名
	private String hip_table_name;
	
	//hip表中接收导入的字段
	private String hip_columns;
	
	//试图唯一值（用于更新或删除）
	private String unique_column;
	
	//是否定时同步
	private String is_sync;

	public String getDblink_sync_name() {
		return dblink_sync_name;
	}
	public void setDblink_sync_name(String dblink_sync_name) {
		this.dblink_sync_name = dblink_sync_name;
	}
	public String getDblink_code() {
		return dblink_code;
	}
	public void setDblink_code(String dblink_code) {
		this.dblink_code = dblink_code;
	}
	public String getDblink_table_code() {
		return dblink_table_code;
	}
	public void setDblink_table_code(String dblink_table_code) {
		this.dblink_table_code = dblink_table_code;
	}
	public String getDblink_table_name() {
		return dblink_table_name;
	}
	public void setDblink_table_name(String dblink_table_name) {
		this.dblink_table_name = dblink_table_name;
	}
	public String getDblink_columns() {
		return dblink_columns;
	}
	public void setDblink_columns(String dblink_columns) {
		this.dblink_columns = dblink_columns;
	}
	public String getHip_table_code() {
		return hip_table_code;
	}
	public void setHip_table_code(String hip_table_code) {
		this.hip_table_code = hip_table_code;
	}
	public String getHip_table_name() {
		return hip_table_name;
	}
	public void setHip_table_name(String hip_table_name) {
		this.hip_table_name = hip_table_name;
	}
	public String getHip_columns() {
		return hip_columns;
	}
	public void setHip_columns(String hip_columns) {
		this.hip_columns = hip_columns;
	}
	public String getUnique_column() {
		return unique_column;
	}
	public void setUnique_column(String unique_column) {
		this.unique_column = unique_column;
	}
	public String getIs_sync() {
		return is_sync;
	}
	public void setIs_sync(String is_sync) {
		this.is_sync = is_sync;
	}
}
