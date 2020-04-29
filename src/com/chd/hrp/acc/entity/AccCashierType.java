/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 出纳类型<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCashierType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	
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
	 * 出纳类型编码
	 */
	private String tell_type_code;
	/**
	 * 出纳类型名称
	 */
	private String tell_type_name;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;

	private String nature_code;
	
	private String kind_code;
	
	private String vouch_type_code;
	
	private String vouch_type_name;
	
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
	public String getTell_type_code() {
		return tell_type_code;
	}
	public void setTell_type_code(String tell_type_code) {
		this.tell_type_code = tell_type_code;
	}
	public String getTell_type_name() {
		return tell_type_name;
	}
	public void setTell_type_name(String tell_type_name) {
		this.tell_type_name = tell_type_name;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public String getNature_code() {
		return nature_code;
	}
	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}
	public String getKind_code() {
		return kind_code;
	}
	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}
	public String getVouch_type_code() {
		return vouch_type_code;
	}
	public void setVouch_type_code(String vouch_type_code) {
		this.vouch_type_code = vouch_type_code;
	}
	public String getVouch_type_name() {
		return vouch_type_name;
	}
	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}
	
}