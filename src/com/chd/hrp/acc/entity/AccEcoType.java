/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 支出经济分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccEcoType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 支出经济分类ID
	 */
	private Long eco_id;
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
	 * 年度
	 */
	private String acc_year;
	/**
	 * 支出经济分类编码
	 */
	private String eco_code;
	/**
	 * 支出经济分类名称
	 */
	private String eco_name;
	/**
	 * 上级编码
	 */
	private String super_code;
	/**
	 * 分类级次
	 */
	private Integer eco_level;
	/**
	 * 是否末级
	 */
	private Integer is_last;
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
	 * 备注
	 */
	private String note;

	/**
	 * 设置 支出经济分类ID
	 */
	public void setEco_id(Long value) {
		this.eco_id = value;
	}
	/**
	 * 获取 支出经济分类ID
	 */	
	public Long getEco_id() {
		return this.eco_id;
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
	 * 设置 年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 支出经济分类编码
	 */
	public void setEco_code(String value) {
		this.eco_code = value;
	}
	/**
	 * 获取 支出经济分类编码
	 */	
	public String getEco_code() {
		return this.eco_code;
	}
	/**
	 * 设置 支出经济分类名称
	 */
	public void setEco_name(String value) {
		this.eco_name = value;
	}
	/**
	 * 获取 支出经济分类名称
	 */	
	public String getEco_name() {
		return this.eco_name;
	}
	/**
	 * 设置 上级编码
	 */
	public void setSuper_code(String value) {
		this.super_code = value;
	}
	/**
	 * 获取 上级编码
	 */	
	public String getSuper_code() {
		return this.super_code;
	}
	/**
	 * 设置 分类级次
	 */
	public void setEco_level(Integer value) {
		this.eco_level = value;
	}
	/**
	 * 获取 分类级次
	 */	
	public Integer getEco_level() {
		return this.eco_level;
	}
	/**
	 * 设置 是否末级
	 */
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	/**
	 * 获取 是否末级
	 */	
	public Integer getIs_last() {
		return this.is_last;
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
	 * 设置 备注
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 备注
	 */	
	public String getNote() {
		return this.note;
	}
}