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
 * 051203 保养记录资产明细
 * @Table:
 * ASS_MAINTAIN_REC_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMaintainRecAss implements Serializable { 

	
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
	private String ass_spec;
	private String ass_mondl;
	private String ass_brand;
	private String ass_code;
	private String ass_name;
	private String fac_name;
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

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
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

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	/**
	 * 保养ID
	 */
	private Long rec_id;
	
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	private Integer detail_id ;
	
	/**
	 * 保养费用
	 */
	private Double maintain_money;
	
	/**
	 * 工时单位
	 */
	private Integer maintain_unit;
	
	private String maintain_unitt;
	
	public String getMaintain_unitt() {
		return maintain_unitt;
	}

	public void setMaintain_unitt(String maintain_unitt) {
		this.maintain_unitt = maintain_unitt;
	}
	/**
	 * 保养工时
	 */
	private Double maintain_hours;
	

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
	
	
	
	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}

	/**
	* 设置 保养费用
	* @param value 
	*/
	public void setMaintain_money(Double value) {
		this.maintain_money = value;
	}
	
	/**
	* 获取 保养费用
	* @return Double
	*/
	public Double getMaintain_money() {
		return this.maintain_money;
	}
	/**
	* 设置 工时单位
	* @param value 
	*/
	public void setMaintain_unit(Integer value) {
		this.maintain_unit = value;
	}
	
	/**
	* 获取 工时单位
	* @return Integer
	*/
	public Integer getMaintain_unit() {
		return this.maintain_unit;
	}
	/**
	* 设置 保养工时
	* @param value 
	*/
	public void setMaintain_hours(Double value) {
		this.maintain_hours = value;
	}
	
	/**
	* 获取 保养工时
	* @return Double
	*/
	public Double getMaintain_hours() {
		return this.maintain_hours;
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