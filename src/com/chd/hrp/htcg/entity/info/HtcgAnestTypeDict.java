package com.chd.hrp.htcg.entity.info;

import java.io.Serializable;

public class HtcgAnestTypeDict implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String anest_type_code;
	private String anest_type_name;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getAnest_type_code() {
		return anest_type_code;
	}
	public String getAnest_type_name() {
		return anest_type_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setAnest_type_code(String anest_type_code) {
		this.anest_type_code = anest_type_code;
	}
	public void setAnest_type_name(String anest_type_name) {
		this.anest_type_name = anest_type_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	
	
	
}
