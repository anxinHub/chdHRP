/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.maintain;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051203 保养记录项目明细
 * @Table:
 * ASS_MAINTAIN_REC_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMaintainRecItem implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 保养ID
	 */
	private Long rec_id;
	
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	private String detail_id ;
	
	private String ass_id ;
	
	private String ass_no ;
	
	private String ass_code ;
	
	private String ass_name ;
	
	private String ass_spec ;
	
	private String ass_mondl ;
	
	private String fac_name ;
	private String fac_code ;
	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}
	private String maintain_money ;
	
	private String maintain_unit ;
	
	private String maintain_hours;
	
	public String getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_no() {
		return ass_no;
	}

	public void setAss_no(String ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	/**
	 * 项目编码
	 */
	private String item_code;
	
	private String maintain_item_code ;
	
	private String maintain_item_name ;
	
	
	public String getMaintain_item_code() {
		return maintain_item_code;
	}

	public void setMaintain_item_code(String maintain_item_code) {
		this.maintain_item_code = maintain_item_code;
	}

	public String getMaintain_item_name() {
		return maintain_item_name;
	}

	public void setMaintain_item_name(String maintain_item_name) {
		this.maintain_item_name = maintain_item_name;
	}
	
	
	public String getMaintain_money() {
		return maintain_money;
	}

	public void setMaintain_money(String maintain_money) {
		this.maintain_money = maintain_money;
	}

	public String getMaintain_unit() {
		return maintain_unit;
	}

	public void setMaintain_unit(String maintain_unit) {
		this.maintain_unit = maintain_unit;
	}

	public String getMaintain_hours() {
		return maintain_hours;
	}

	public void setMaintain_hours(String maintain_hours) {
		this.maintain_hours = maintain_hours;
	}
	/**
	 * 项目名称
	 */
	private String  item_name;
	
	/**
	 * 是否执行
	 */
	private Integer is_maintain;
	
	/**
	 * 是否正常
	 */
	private Integer is_normal;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 保养ID
	* @param value 
	*/
	public void setRec_id(Long value) {
		this.rec_id = value;
	}
	
	/**
	* 获取 保养ID
	* @return Long
	*/
	public Long getRec_id() {
		return this.rec_id;
	}
	/**
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	/**
	* 设置 是否执行
	* @param value 
	*/
	public void setIs_maintain(Integer value) {
		this.is_maintain = value;
	}
	
	/**
	* 获取 是否执行
	* @return Integer
	*/
	public Integer getIs_maintain() {
		return this.is_maintain;
	}
	/**
	* 设置 是否正常
	* @param value 
	*/
	public void setIs_normal(Integer value) {
		this.is_normal = value;
	}
	
	/**
	* 获取 是否正常
	* @return Integer
	*/
	public Integer getIs_normal() {
		return this.is_normal;
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