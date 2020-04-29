package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipDblink implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dblink_code;

	private String dblink_name;

	private String db_user;

	private String db_psw;

	private String db_url;
	
	private String db_port;
	
	private String db_sid;

	private String dblink_note;

	
	public String getDblink_code() {
		return dblink_code;
	}
	public void setDblink_code(String dblink_code) {
		this.dblink_code = dblink_code;
	}
	public String getDblink_name() {
		return dblink_name;
	}
	public void setDblink_name(String dblink_name) {
		this.dblink_name = dblink_name;
	}
	public String getDb_user() {
		return db_user;
	}
	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}
	public String getDb_psw() {
		return db_psw;
	}
	public void setDb_psw(String db_psw) {
		this.db_psw = db_psw;
	}
	public String getDb_url() {
		return db_url;
	}
	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}
	public String getDb_port() {
		return db_port;
	}
	public void setDb_port(String db_port) {
		this.db_port = db_port;
	}
	public String getDb_sid() {
		return db_sid;
	}
	public void setDb_sid(String db_sid) {
		this.db_sid = db_sid;
	}
	public String getDblink_note() {
		return dblink_note;
	}
	public void setDblink_note(String dblink_note) {
		this.dblink_note = dblink_note;
	}
}
