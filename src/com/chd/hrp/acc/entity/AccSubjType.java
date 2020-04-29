/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科目类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccSubjType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 科目类别编码
	 */
	private String subj_type_code;
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
	 * 科目类别名称
	 */
	private String subj_type_name;
	/**
	 * 科目类别属性
	 */
	private String kind_code;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;

	/**
	 * 设置 科目类别编码
	 */
	public void setSubj_type_code(String value) {
		this.subj_type_code = value;
	}
	/**
	 * 获取 科目类别编码
	 */	
	public String getSubj_type_code() {
		return this.subj_type_code;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	 * 设置 科目类别名称
	 */
	public void setSubj_type_name(String value) {
		this.subj_type_name = value;
	}
	/**
	 * 获取 科目类别名称
	 */	
	public String getSubj_type_name() {
		return this.subj_type_name;
	}
	/**
	 * 设置 科目类别属性
	 */
	public void setKind_code(String value) {
		this.kind_code = value;
	}
	/**
	 * 获取 科目类别属性
	 */	
	public String getKind_code() {
		return this.kind_code;
	}
	/**
	 * 设置 拼音码
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	/**
	 * 获取 拼音码
	 */	
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	/**
	 * 获取 五笔码
	 */	
	public String getWbx_code() {
		return this.wbx_code;
	}
}