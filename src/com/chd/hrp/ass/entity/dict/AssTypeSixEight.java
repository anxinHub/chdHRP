package com.chd.hrp.ass.entity.dict;

public class AssTypeSixEight {
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
	
	/**
	 * 编码
	 */
	private String type_code;
	
	/**
	 * 名称
	 */
	private String type_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String supper_code;
	private int type_level;
	private int is_last;
	
	
	
	public String getSupper_code() {
		return supper_code;
	}

	public void setSupper_code(String supper_code) {
		this.supper_code = supper_code;
	}

	public int getType_level() {
		return type_level;
	}

	public void setType_level(int type_level) {
		this.type_level = type_level;
	}

	public int getIs_last() {
		return is_last;
	}

	public void setIs_last(int is_last) {
		this.is_last = is_last;
	}

	public String getType_code() {
		return type_code;
	}
	
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	
	public String getType_name() {
		return type_name;
	}
	
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 获取 五笔码
	* @return String
	*/
    public String getWbx_code() {
    	return wbx_code;
    }

    /**
	* 设置 五笔码
	* @param value 
	*/
    public void setWbx_code(String wbx_code) {
    	this.wbx_code = wbx_code;
    }
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}
