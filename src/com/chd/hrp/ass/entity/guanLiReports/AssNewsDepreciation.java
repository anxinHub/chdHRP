package com.chd.hrp.ass.entity.guanLiReports;

import java.io.Serializable;
import java.util.Date;

public class AssNewsDepreciation implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7989328482619033916L;

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
	 * ID
	 */
	private String	 change_no;
	
	/**
	 * 折旧期间
	 */
	private String year_month;
	
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	
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

	/**
	 * @return the charge_id
	 */
	public String getChange_no() {
		return change_no;
	}

	/**
	 * @param charge_id the charge_id to set
	 */
	public void setCharge_id(String change_no) {
		this.change_no = change_no;
	}

	/**
	 * @return the old_depre
	 */
	public Double getOld_depre() {
		return old_depre;
	}

	/**
	 * @param old_depre the old_depre to set
	 */
	public void setOld_depre(Double old_depre) {
		this.old_depre = old_depre;
	}

	/**
	 * @return the charge_depre
	 */
	public Double getChange_depre() {
		return change_depre;
	}

	/**
	 * @param charge_depre the charge_depre to set
	 */
	public void setChange_depre(Double change_depre) {
		this.change_depre = change_depre;
	}

	/**
	 * @return the new_depre
	 */
	public Double getNew_depre() {
		return new_depre;
	}

	/**
	 * @param new_depre the new_depre to set
	 */
	public void setNew_depre(Double new_depre) {
		this.new_depre = new_depre;
	}

	/**
	 * @return the year_month
	 */
	public String getYear_month() {
		return year_month;
	}

	/**
	 * @param year_month the year_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

}
