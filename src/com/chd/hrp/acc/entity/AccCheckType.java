/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 核算类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCheckType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 用自增ID
	 */
	private Long check_type_id;
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
	 * 表名
	 */
	private String check_table;
	public String getCheck_table() {
		return check_table;
	}
	public void setCheck_table(String check_table) {
		this.check_table = check_table;
	}
	/**
	 * 核算类名称
	 */
	private String check_type_name;
	private String check_type_code;
	/**
	 * 排序号
	 */
	private Long sort_code;
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
	 * 是否系统核算
	 */
	private Integer is_sys;
	
	private String column_id ;
	
	private String column_no ;
	
	private String column_code; 
	
	private String column_name;
	private String column_check;
	private Integer is_change ; 
	private String z_code;
	private String acc_year;
	/**
	 * 设置 用自增ID
	 */
	public void setCheck_type_id(Long value) {
		this.check_type_id = value;
	}
	/**
	 * 获取 用自增ID
	 */	
	public Long getCheck_type_id() {
		return this.check_type_id;
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
	 * 设置 核算类名称
	 */
	public void setCheck_type_name(String value) {
		this.check_type_name = value;
	}
	/**
	 * 获取 核算类名称
	 */	
	public String getCheck_type_name() {
		return this.check_type_name;
	}
	
	public Long getSort_code() {
		return sort_code;
	}
	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
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
	/**
	 * 设置 是否系统核算
	 */
	public void setIs_sys(Integer value) {
		this.is_sys = value;
	}
	/**
	 * 获取 是否系统核算
	 */	
	public Integer getIs_sys() {
		return this.is_sys;
	}
	public String getColumn_id() {
		return column_id;
	}
	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}
	public String getColumn_no() {
		return column_no;
	}
	public void setColumn_no(String column_no) {
		this.column_no = column_no;
	}
	public String getColumn_code() {
		return column_code;
	}
	public void setColumn_code(String column_code) {
		this.column_code = column_code;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public Integer getIs_change() {
		return is_change;
	}
	public void setIs_change(Integer is_change) {
		this.is_change = is_change;
	}
	public String getColumn_check() {
		return column_check;
	}
	public void setColumn_check(String column_check) {
		this.column_check = column_check;
	}
	/**
	 * @return the check_type_code
	 */
	public String getCheck_type_code() {
		return check_type_code;
	}
	/**
	 * @param check_type_code the check_type_code to set
	 */
	public void setCheck_type_code(String check_type_code) {
		this.check_type_code = check_type_code;
	}
	public String getZ_code() {
		return z_code;
	}
	public void setZ_code(String z_code) {
		this.z_code = z_code;
	}
	/**
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}
	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	
}