/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.accessory;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 资产附件_专业设备
 * @Table:
 * ASS_ACCESSORY_INIT_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssAccessoryInitSpecial implements Serializable {

	
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
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 附件编码
	 */
	private String accessory_code;
	
	/**
	 * 附件名称
	 */
	private String accessory_name;
	
	/**
	 * 附件数量
	 */
	private Double accessory_amount;
	
	/**
	 * 附件单价
	 */
	private Double accessory_price;
	
	/**
	 * 附件金额
	 */
	private Double accessory_money;
	
	/**
	 * 资产性质
	 */
	private String naturs_code;
	
	/**
	 * 附卡
	 */
	private String accessory_card_no;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
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
	* 设置 卡片编号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 附件编码
	* @param value 
	*/
	public void setAccessory_code(String value) {
		this.accessory_code = value;
	}
	
	/**
	* 获取 附件编码
	* @return String
	*/
	public String getAccessory_code() {
		return this.accessory_code;
	}
	/**
	* 设置 附件名称
	* @param value 
	*/
	public void setAccessory_name(String value) {
		this.accessory_name = value;
	}
	
	/**
	* 获取 附件名称
	* @return String
	*/
	public String getAccessory_name() {
		return this.accessory_name;
	}
	/**
	* 设置 附件数量
	* @param value 
	*/
	public void setAccessory_amount(Double value) {
		this.accessory_amount = value;
	}
	
	/**
	* 获取 附件数量
	* @return Double
	*/
	public Double getAccessory_amount() {
		return this.accessory_amount;
	}
	/**
	* 设置 附件单价
	* @param value 
	*/
	public void setAccessory_price(Double value) {
		this.accessory_price = value;
	}
	
	/**
	* 获取 附件单价
	* @return Double
	*/
	public Double getAccessory_price() {
		return this.accessory_price;
	}
	/**
	* 设置 附件金额
	* @param value 
	*/
	public void setAccessory_money(Double value) {
		this.accessory_money = value;
	}
	
	/**
	* 获取 附件金额
	* @return Double
	*/
	public Double getAccessory_money() {
		return this.accessory_money;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setNaturs_code(String value) {
		this.naturs_code = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getNaturs_code() {
		return this.naturs_code;
	}
	/**
	* 设置 附卡
	* @param value 
	*/
	public void setAccessory_card_no(String value) {
		this.accessory_card_no = value;
	}
	
	/**
	* 获取 附卡
	* @return String
	*/
	public String getAccessory_card_no() {
		return this.accessory_card_no;
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
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
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