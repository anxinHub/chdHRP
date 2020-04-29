package com.chd.hrp.med.entity.info.basic;

import java.io.Serializable;

public class MedJx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 用途编码
	 */	
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	private String med_jx_id;
	private String med_jx_name;
	private int is_stop;
	private String note;
	/**
	 * @return the group_id
	 */
	public Long getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return the hos_id
	 */
	public Long getHos_id() {
		return hos_id;
	}
	/**
	 * @param hos_id the hos_id to set
	 */
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	/**
	 * @return the copy_code
	 */
	public String getCopy_code() {
		return copy_code;
	}
	/**
	 * @param copy_code the copy_code to set
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	/**
	 * @return the wbx_code
	 */
	public String getWbx_code() {
		return wbx_code;
	}
	/**
	 * @param wbx_code the wbx_code to set
	 */
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	/**
	 * @return the spell_code
	 */
	public String getSpell_code() {
		return spell_code;
	}
	/**
	 * @param spell_code the spell_code to set
	 */
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	/**
	 * @return the med_jx_id
	 */
	public String getMed_jx_id() {
		return med_jx_id;
	}
	/**
	 * @param med_jx_id the med_jx_id to set
	 */
	public void setMed_jx_id(String med_jx_id) {
		this.med_jx_id = med_jx_id;
	}
	/**
	 * @return the med_jx_name
	 */
	public String getMed_jx_name() {
		return med_jx_name;
	}
	/**
	 * @param med_jx_name the med_jx_name to set
	 */
	public void setMed_jx_name(String med_jx_name) {
		this.med_jx_name = med_jx_name;
	}
	/**
	 * @return the is_stop
	 */
	public int getIs_stop() {
		return is_stop;
	}
	/**
	 * @param is_stop the is_stop to set
	 */
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
