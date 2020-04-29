/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.depre.change.source;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动资金来源(房屋及建筑物)
 * @Table:
 * ASS_DEPRE_SOURCE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssDepreSourceHouse implements Serializable {

	
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
	
	/**
	 * 原累计折旧
	 */
	private Double old_depre;
	
	/**
	 * 变动累计折旧
	 */
	private Double change_depre;
	
	/**
	 * 新累计折旧
	 */
	private Double new_depre;
	
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
	* 设置 原累计折旧
	* @param value 
	*/
	public void setOld_depre(Double value) {
		this.old_depre = value;
	}
	
	/**
	* 获取 原累计折旧
	* @return Double
	*/
	public Double getOld_depre() {
		return this.old_depre;
	}
	/**
	* 设置 变动累计折旧
	* @param value 
	*/
	public void setChange_depre(Double value) {
		this.change_depre = value;
	}
	
	/**
	* 获取 变动累计折旧
	* @return Double
	*/
	public Double getChange_depre() {
		return this.change_depre;
	}
	/**
	* 设置 新累计折旧
	* @param value 
	*/
	public void setNew_depre(Double value) {
		this.new_depre = value;
	}
	
	/**
	* 获取 新累计折旧
	* @return Double
	*/
	public Double getNew_depre() {
		return this.new_depre;
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