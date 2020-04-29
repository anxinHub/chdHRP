package com.chd.hrp.ass.entity.tran.out.map;

public class AssInOutMapInassets {
	/**
	 * 入库集团ID
	 */
	private Long group_id;
	
	/**
	 * 入库医院ID
	 */
	private Long hos_id;
	
	/**
	 * 入库账套编码
	 */
	private String copy_code;
	
	/**
	 * 入库单号
	 */
	private String ass_in_no;
	
	/**
	 * 出库集团ID
	 */
	private String out_no;

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

	public String getAss_in_no() {
		return ass_in_no;
	}

	public void setAss_in_no(String ass_in_no) {
		this.ass_in_no = ass_in_no;
	}

	public String getOut_no() {
		return out_no;
	}

	public void setOut_no(String out_no) {
		this.out_no = out_no;
	}
	
	
}
