/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证类型<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccVouchType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 凭证类型编码
	 */
	private String vouch_type_code;
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
	 * 凭证类型名称
	 */
	private String vouch_type_name;
	/**
	 * 凭证类型简称
	 */
	private String vouch_type_short;
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
	 * 设置 凭证类型编码
	 */
	public void setVouch_type_code(String value) {
		this.vouch_type_code = value;
	}
	/**
	 * 获取 凭证类型编码
	 */	
	public String getVouch_type_code() {
		return this.vouch_type_code;
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
	 * 设置 凭证类型名称
	 */
	public void setVouch_type_name(String value) {
		this.vouch_type_name = value;
	}
	/**
	 * 获取 凭证类型名称
	 */	
	public String getVouch_type_name() {
		return this.vouch_type_name;
	}
	/**
	 * 设置 凭证类型简称
	 */
	public void setVouch_type_short(String value) {
		this.vouch_type_short = value;
	}
	/**
	 * 获取 凭证类型简称
	 */	
	public String getVouch_type_short() {
		return this.vouch_type_short;
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
	/**
	 * 设置 是否停用
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 是否停用
	 */	
	public Integer getIs_stop() {
		return this.is_stop;
	}
}