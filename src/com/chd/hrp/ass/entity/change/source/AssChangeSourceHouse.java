/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.change.source;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050805 资产原值变动资金来源(房屋及建筑物)
 * @Table:
 * ASS_Change_SOURCE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssChangeSourceHouse implements Serializable {

	
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
	 * 变更单据号
	 */
	private String change_no;
	
	/**
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 资金来源
	 */
	private Long source_id;
	
	private String source_code;
	
	private String source_name;
	
	/**
	 * 原有原值
	 */
	private Double old_price;
	
	/**
	 * 变动原值
	 */
	private Double change_price;
	
	/**
	 * 新原值
	 */
	private Double price;
	
	/**
	 * 备注
	 */
	private String note;
	

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
	* 设置 变更单据号
	* @param value 
	*/
	public void setChange_no(String value) {
		this.change_no = value;
	}
	
	/**
	* 获取 变更单据号
	* @return String
	*/
	public String getChange_no() {
		return this.change_no;
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
	* 设置 资金来源
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
	}
	/**
	* 设置 原有原值
	* @param value 
	*/
	public void setOld_price(Double value) {
		this.old_price = value;
	}
	
	/**
	* 获取 原有原值
	* @return Double
	*/
	public Double getOld_price() {
		return this.old_price;
	}
	/**
	* 设置 变动原值
	* @param value 
	*/
	public void setChange_price(Double value) {
		this.change_price = value;
	}
	
	/**
	* 获取 变动原值
	* @return Double
	*/
	public Double getChange_price() {
		return this.change_price;
	}
	/**
	* 设置 新原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 新原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
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

	public String getSource_code() {
		return source_code;
	}

	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	
	
}