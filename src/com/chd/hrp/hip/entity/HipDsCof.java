package com.chd.hrp.hip.entity;

import java.io.Serializable;

public class HipDsCof implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ds_code;

	private String ds_name;

	private String ds_note;

	private String db_user;

	private String db_psw;

	private String url_str;

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

	public String getDs_note() {
		return ds_note;
	}

	public void setDs_note(String ds_note) {
		this.ds_note = ds_note;
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

	public String getUrl_str() {
		return url_str;
	}

	public void setUrl_str(String url_str) {
		this.url_str = url_str;
	}

}
