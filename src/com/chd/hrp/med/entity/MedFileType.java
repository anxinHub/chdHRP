package com.chd.hrp.med.entity;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 08114 文档分类
 * @Table:
 * MED_FILE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public class MedFileType  implements Serializable{

	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 *集团ID
	 */
	private Long group_id;
	
	/**
	 *医院ID
	 */
	private Long hos_id;
	
	/**
	 *账套编码
	 */
	private String copy_code;
	
	/**
	 *文档类别编码
	 */
	private String type_code;
	
	/**
	 *文档类别名称
	 */
	private String type_name;
	
	/**
	 *五笔码
	 */
	private String wbx_code;
	
	/**
	 *拼音码
	 */
	private String spell_code;
	
	/**
	 *是否停用
	 */
	private Integer is_stop;
	
	/**
	 *备注
	 */
	private String note;
	
	/**
	 *获取 集团ID
	 */
	public Long getGroup_id() {
		return group_id;
	}
	
	/**
	 *设置 集团ID
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	
	/**
	 *获取 医院ID
	 */
	public Long getHos_id() {
		return hos_id;
	}
	
	/**
	 *设置 医院ID
	 */
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	
	/**
	 *获取 账套编码
	 */
	public String getCopy_code() {
		return copy_code;
	}
	
	/**
	 *设置 账套编码
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
	/**
	 *获取 文档类别编码
	 */
	public String getType_code() {
		return type_code;
	}
	
	/**
	 *设置 文档类别编码
	 */
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	
	/**
	 *获取文档类别名称
	 */
	public String getType_name() {
		return type_name;
	}
	
	/**
	 *设置 文档类别名称
	 */
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
	/**
	 *获取 五笔码
	 */
	public String getWbx_code() {
		return wbx_code;
	}
	
	/**
	 *设置 五笔码
	 */
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	
	/**
	 *获取 拼音码
	 */
	public String getSpell_code() {
		return spell_code;
	}
	
	/**
	 *设置 拼音码
	 */
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	
	/**
	 *获取 是否停用
	 */
	public Integer getIs_stop() {
		return is_stop;
	}
	
	/**
	 *设置 是否停用
	 */
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	
	/**
	 *获取 备注
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 *设置 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
