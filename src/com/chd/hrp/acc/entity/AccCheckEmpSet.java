/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 职工自定义属性<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCheckEmpSet implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	private String attr_code;
	
	private String attr_name;
	
	private String note;
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
	 * @return the attr_code
	 */
	public String getAttr_code() {
		return attr_code;
	}
	/**
	 * @param attr_code the attr_code to set
	 */
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	/**
	 * @return the attr_name
	 */
	public String getAttr_name() {
		return attr_name;
	}
	/**
	 * @param attr_name the attr_name to set
	 */
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
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
	 * @return the is_stop
	 */
	public Integer getIs_stop() {
		return is_stop;
	}
	/**
	 * @param is_stop the is_stop to set
	 */
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	
	
	
	
}