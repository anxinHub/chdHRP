package com.chd.hrp.ass.entity.balance;

public class AssBalanceInitState {

	
	private static final long serialVersionUID = 5454155825314635342L;
	

	/**
	 * 集体ID
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
	
	
	private String mod_code;
	
	private Integer is_init_in; 
	
	private Integer  is_init_card;

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

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public Integer getIs_init_in() {
		return is_init_in;
	}

	public void setIs_init_in(Integer is_init_in) {
		this.is_init_in = is_init_in;
	}

	public Integer getIs_init_card() {
		return is_init_card;
	}

	public void setIs_init_card(Integer is_init_card) {
		this.is_init_card = is_init_card;
	}
	
}
