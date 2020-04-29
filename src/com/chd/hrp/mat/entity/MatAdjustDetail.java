package com.chd.hrp.mat.entity;

import java.io.Serializable;

public class MatAdjustDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套
	 */
	private String copy_code;
	
	/**
	 * 调价单id
	 */
	private Long adjust_id;
	
	/**
	 * 调价单明细id
	 */
	private Long adjust_detail_id;
	
	/**
	 * 材料id
	 */
	private Long inv_id;
	
	/**
	 * 材料变更
	 */
	private Long inv_no;
	
	/**
	 * 材料编码
	 */
	private String inv_code;
	
	/**
	 * 材料名称
	 */
	private String inv_name;
	
	/**
	 * 原零售价
	 */
	private Double old_sell_price;
	
	/**
	 * 新零售价
	 */
	private Double new_sell_price;
	
	/**
	 * 调价原因
	 */
	private String adjust_reason;
	
	/**
	 * 原计划价
	 */
	private Double old_price;
	
	/**
	 * 新计划价
	 */
	private Double new_price;
	
	/**
	* 规格型号
	* 
	*/
	private String inv_model;
	
	/**
	* 计量单位
	* 
	*/
	private String unit_name;
	
	/**
	* 生产厂商
	* 
	*/
	private String fac_name;
	
	/**
	 * 导入错误信息
	 */
	private String error_type;
	
	

	/**
	* 获取 集团ID
	* 
	*/
	public Long getGroup_id() {
		return group_id;
	}
	
	/**
	* 设置 集团ID
	* 
	*/
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	
	/**
	* 获取医院ID
	* 
	*/
	public Long getHos_id() {
		return hos_id;
	}
	
	/**
	* 设置 医院ID
	* 
	*/
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	
	/**
	* 获取 账套
	* 
	*/
	public String getCopy_code() {
		return copy_code;
	}
	
	/**
	* 设置 账套
	* 
	*/
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
	/**
	* 获取 调价单id
	* 
	*/
	public Long getAdjust_id() {
		return adjust_id;
	}

	/**
	* 设置调价单id
	* 
	*/
	public void setAdjust_id(Long adjust_id) {
		this.adjust_id = adjust_id;
	}
	
	/**
	* 获取  调价单明细id
	* 
	*/
	public Long getAdjust_detail_id() {
		return adjust_detail_id;
	}

	/**
	* 设置  调价单明细id
	* 
	*/
	public void setAdjust_detail_id(Long adjust_detail_id) {
		this.adjust_detail_id = adjust_detail_id;
	}

	/**
	* 获取  材料id
	* 
	*/
	public Long getInv_id() {
		return inv_id;
	}
	
	/**
	* 设置  材料id
	* 
	*/
	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}
	
	/**
	* 获取  材料变更号
	* 
	*/
	public Long getInv_no() {
		return inv_no;
	}
	
	/**
	* 设置 材料变更号
	* 
	*/
	public void setInv_no(Long inv_no) {
		this.inv_no = inv_no;
	}
	
	/**
	* 获取  材料编码
	* 
	*/
	public String getInv_code() {
		return inv_code;
	}
	
	/**
	* 设置 材料编码
	* 
	*/
	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}
	
	/**
	* 获取  原零售价
	* 
	*/
	public Double getOld_sell_price() {
		return old_sell_price;
	}
	
	/**
	* 设置 原零售价
	* 
	*/
	public void setOld_sell_price(Double old_sell_price) {
		this.old_sell_price = old_sell_price;
	}
	
	/**
	* 获取  新零售价
	* 
	*/
	public Double getNew_sell_price() {
		return new_sell_price;
	}
	
	/**
	* 设置  新零售价
	* 
	*/
	public void setNew_sell_price(Double new_sell_price) {
		this.new_sell_price = new_sell_price;
	}
	
	/**
	* 获取  调价原因
	* 
	*/
	public String getAdjust_reason() {
		return adjust_reason;
	}
	
	/**
	* 设置 调价原因
	* 
	*/
	public void setAdjust_reason(String adjust_reason) {
		this.adjust_reason = adjust_reason;
	}
	
	/**
	* 获取  原计划价
	* 
	*/
	public Double getOld_price() {
		return old_price;
	}
	
	/**
	* 设置  原计划价
	* 
	*/
	public void setOld_price(Double old_price) {
		this.old_price = old_price;
	}
	
	/**
	* 获取  新计划价
	* 
	*/
	public Double getNew_price() {
		return new_price;
	}
	
	/**
	* 设置  新计划价
	* 
	*/
	public void setNew_price(Double new_price) {
		this.new_price = new_price;
	}
	
	/**
	* 获取  导入错误信息
	* 
	*/
	public String getError_type() {
		return error_type;
	}
	
	/**
	* 设置 导入错误信息
	* 
	*/
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	/**
	* 获取 材料名称
	* 
	*/
	public String getInv_name() {
		return inv_name;
	}
	

	/**
	* 设置 材料名称
	* 
	*/
	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}
	
	/**
	* 获取规格型号
	* 
	*/
	public String getInv_model() {
		return inv_model;
	}
	
	/**
	* 设置 规格型号
	* 
	*/
	public void setInv_model(String inv_model) {
		this.inv_model = inv_model;
	}
	
	/**
	* 获取 计量单位
	* 
	*/
	public String getUnit_name() {
		return unit_name;
	}
	
	/**
	* 设置 计量单位
	* 
	*/
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	
	/**
	* 获取 生产厂商
	* 
	*/
	public String getFac_name() {
		return fac_name;
	}
	
	/**
	* 设置 生产厂商
	* 
	*/
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	
}
	
	
