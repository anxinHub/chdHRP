/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_收费项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostChargeItemArrt implements Serializable {

	
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
	 * 收费类别编码
	 */
	private Long charge_kind_id;
	
	private String charge_kind_code;
	
	private String charge_kind_name;
	
	private Long charge_item_id;
	/**
	 * 收费项目编码
	 */
	private String charge_item_code;
	/**
	 * 收费项目名称
	 */
	private String charge_item_name;
	/**
	 * 单价
	 */
	private double price;
	/**
	 * 停用标志
	 */
	private Integer is_stop;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	
	public String getCharge_kind_code() {
		return charge_kind_code;
	}
	public void setCharge_kind_code(String charge_kind_code) {
		this.charge_kind_code = charge_kind_code;
	}
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	public Long getCharge_kind_id() {
		return charge_kind_id;
	}
	public void setCharge_kind_id(Long charge_kind_id) {
		this.charge_kind_id = charge_kind_id;
	}
	public Long getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(Long charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public String getCharge_kind_name() {
		return charge_kind_name;
	}
	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
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
	 * 设置 收费项目编码
	 */
	public void setCharge_item_code(String value) {
		this.charge_item_code = value;
	}
	/**
	 * 获取 收费项目编码
	 */	
	public String getCharge_item_code() {
		return this.charge_item_code;
	}
	/**
	 * 设置 收费项目名称
	 */
	public void setCharge_item_name(String value) {
		this.charge_item_name = value;
	}
	/**
	 * 获取 收费项目名称
	 */	
	public String getCharge_item_name() {
		return this.charge_item_name;
	}
	/**
	 * 设置 单价
	 */
	public void setPrice(double value) {
		this.price = value;
	}
	/**
	 * 获取 单价
	 */	
	public double getPrice() {
		return this.price;
	}
	/**
	 * 设置 停用标志
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 停用标志
	 */	
	public Integer getIs_stop() {
		return this.is_stop;
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