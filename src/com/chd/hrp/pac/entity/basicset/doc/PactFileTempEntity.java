package com.chd.hrp.pac.entity.basicset.doc;

import java.io.Serializable;

public class PactFileTempEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6888932693806851879L;

	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_type_code;
	private String file_type;

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

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPact_type_code() {
		return pact_type_code;
	}

	public void setPact_type_code(String pact_type_code) {
		this.pact_type_code = pact_type_code;
	}

}
