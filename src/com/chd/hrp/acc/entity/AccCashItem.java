/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 现金流量项目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCashItem implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 现金流量项目ID
	 */
	private Long cash_item_id;
	/**
	 * 现金流量类别ID
	 */
	private Long cash_type_id;
	/**
	 * 现金流量类别名称
	 */
	private String cash_type_name;
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
	 * 现金流量项目编码
	 */
	private String cash_item_code;
	/**
	 * 现金流量项目名称
	 */
	private String cash_item_name;
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
	
	private String cash_dire;
	
	
	public String getCash_dire() {
		return cash_dire;
	}
	public void setCash_dire(String cash_dire) {
		this.cash_dire = cash_dire;
	}
	public String getCash_type_name() {
		return cash_type_name;
	}
	public void setCash_type_name(String cash_type_name) {
		this.cash_type_name = cash_type_name;
	}
	/**
	 * 设置 现金流量项目ID
	 */
	public void setCash_item_id(Long value) {
		this.cash_item_id = value;
	}
	/**
	 * 获取 现金流量项目ID
	 */	
	public Long getCash_item_id() {
		return this.cash_item_id;
	}
	/**
	 * 设置 现金流量类别ID
	 */
	public void setCash_type_id(Long value) {
		this.cash_type_id = value;
	}
	/**
	 * 获取 现金流量类别ID
	 */	
	public Long getCash_type_id() {
		return this.cash_type_id;
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
	 * 设置 现金流量项目编码
	 */
	public void setCash_item_code(String value) {
		this.cash_item_code = value;
	}
	/**
	 * 获取 现金流量项目编码
	 */	
	public String getCash_item_code() {
		return this.cash_item_code;
	}
	/**
	 * 设置 现金流量项目名称
	 */
	public void setCash_item_name(String value) {
		this.cash_item_name = value;
	}
	/**
	 * 获取 现金流量项目名称
	 */	
	public String getCash_item_name() {
		return this.cash_item_name;
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