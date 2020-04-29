/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 08114 药品科室配套表
 * @Table:
 * MED_DEPT_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedDeptMatch implements Serializable {

	
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
	 * 配套表ID
	 */
	private Long dept_match_id;
	
	
	/**
	 * 配套表编码
	 */
	private String dept_match_code;
	
	/**
	 * 配套表名称
	 */
	private Object dept_match_name;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 仓库编码
	 */
	private String store_code;
	
	/**
	 * 仓库名称
	 */
	private String store_name;
	
	/**
	 * 仓库名称
	 */
	private String store_no;
	
	/**
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 科室名称
	 */
	private String dept_name;
	/**
	 * 科室code
	 */
	private String dept_code;
	/**
	 * 科室编码
	 */
	private String dept_no;
	
	/**
	 * 药品材料ID
	 */
	private Long inv_id;
	private Long invid;
	/**
	 * 药品材料编码
	 */
	private String inv_code;
	
	/**
	 * 药品材料名称
	 */
	private String inv_name;
	
	/**
	 * 规格型号
	 */
	private String inv_model;
	
	/**
	 * 计量单位编码
	 */
	private String unit_code;
	

	/**
	 * 计量单位名称
	 */
	private String unit_name;
	
	/**
	 * 数量
	 */
	private Long amount;
	
	private String bid_code;
	
	private String sup_name;
	
	private String fac_name;
	

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
	* 设置 配套表ID
	* @param value 
	*/
	public void setDept_match_id(Long value) {
		this.dept_match_id = value;
	}
	
	/**
	* 获取 配套表ID
	* @return Long
	*/
	public Long getDept_match_id() {
		return this.dept_match_id;
	}
	/**
	* 设置 配套表名称
	* @param value 
	*/
	public void setDept_match_name(Object value) {
		this.dept_match_name = value;
	}
	
	/**
	* 获取 配套表名称
	* @return Object
	*/
	public Object getDept_match_name() {
		return this.dept_match_name;
	}
	
	
	public void setDept_match_code(String value) {
		this.dept_match_code = value;
	}
	
	/**
	* 获取 配套表编码
	* @return Object
	*/
	public String getDept_match_code() {
		 return dept_match_code;
	}
	/**
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
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
	 * 获取 仓库名称
	 */
	public String getStore_name() {
		return store_name;
	}
	

	/**
	 * 设置 仓库名称
	 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	/**
	 * 获取 科室名称
	 */
	public String getDept_name() {
		return dept_name;
	}

	/**
	 * 设置 科室名称
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	

	/**
	 *获取 仓库编码
	 */
	public String getStore_code() {
		return store_code;
	}
	

	/**
	 *设置 仓库编码
	 */
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	/**
	 * 获取 药品材料ID
	 */
	public Long getInv_id() {
		return inv_id;
	}
	
	/**
	 * 设置 药品材料ID
	 */
	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}
	
	/**
	 * 获取 药品材料编码
	 */
	public String getInv_code() {
		return inv_code;
	}
	
	/**
	 * 设置 药品材料编码
	 */
	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}
	
	/**
	 *获取 药品材料名称
	 */
	public String getInv_name() {
		return inv_name;
	}
	
	/**
	 *设置 药品材料名称
	 */
	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}
	
	/**
	 * 获取 规格型号
	 */
	public String getInv_model() {
		return inv_model;
	}
	
	/**
	 * 设置 规格型号
	 */
	public void setInv_model(String inv_model) {
		this.inv_model = inv_model;
	}
	
	/**
	 * 获取 计量单位编码
	 */
	public String getUnit_code() {
		return unit_code;
	}
	
	/**
	 * 设置 计量单位编码
	 */
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	
	/**
	 * 获取 计量单位名称
	 */
	public String getUnit_name() {
		return unit_name;
	}
	
	/**
	 * 设置 计量单位名称
	 */
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	
	/**
	 * 获取 数量
	 */
	public Long getAmount() {
		return amount;
	}
	
	/**
	 * 设置 数量
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	/**
	 * 获取 仓库编码
	 */
	public String getStore_no() {
		return store_no;
	}
	
	/**
	 * 设置 仓库编码
	 */
	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}
	
	/**
	 * 获取 科室编码
	 */
	public String getDept_no() {
		return dept_no;
	}
	
	/**
	 * 设置 科室编码
	 */
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public Long getInvid() {
		return invid;
	}

	public void setInvid(Long invid) {
		this.invid = invid;
	}

	public String getBid_code() {
		return bid_code;
	}

	public void setBid_code(String bid_code) {
		this.bid_code = bid_code;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	
}