package com.chd.hrp.ass.entity.assdisposal.inassets;

import java.io.Serializable;

public class AssDisposalArMapInassets implements Serializable{
	
private static final long serialVersionUID = 4168035048944236150L;
	
	/**
	 * 集团ID
	 */
	private Long group_id;

	/**
	 * 医院ID
	 */
	private Long hos_id;

	/**
	 * 账套编码
	 */
	private String copy_code;

	/**
	 * 处置单号
	 */
	private String dis_r_no;
	
	private String dis_a_no;
	
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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getDis_r_no() {
		return dis_r_no;
	}

	public void setDis_r_no(String dis_r_no) {
		this.dis_r_no = dis_r_no;
	}

	public String getDis_a_no() {
		return dis_a_no;
	}

	public void setDis_a_no(String dis_a_no) {
		this.dis_a_no = dis_a_no;
	}

	public Long getDispose_type() {
		return dispose_type;
	}

	public void setDispose_type(Long dispose_type) {
		this.dispose_type = dispose_type;
	}

	private Long dispose_type;

}
