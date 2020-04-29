/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 资产字典
 * @Table:
 * BUDG_ASSET_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssetDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 资产性质
	 */
	private Integer asset_nature;
	
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
	 * 资产编码
	 */
	private String asset_code;
	
	/**
	 * 资产名称
	 */
	private String asset_name;
	
	/**
	 * 资产类别编码
	 */
	private String asset_type_code;
	
	/**
	 * 是否计提折旧
	 */
	private Integer is_depre;
	
	/**
	 * 折旧年限
	 */
	private Long depre_year;
	
	/**
	 * 0:不停用 1:停用
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
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setAsset_nature(Integer value) {
		this.asset_nature = value;
	}
	
	/**
	* 获取 资产性质
	* @return Integer
	*/
	public Integer getAsset_nature() {
		return this.asset_nature;
	}
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
	* 设置 资产编码
	* @param value 
	*/
	public void setAsset_code(String value) {
		this.asset_code = value;
	}
	
	/**
	* 获取 资产编码
	* @return String
	*/
	public String getAsset_code() {
		return this.asset_code;
	}
	/**
	* 设置 资产名称
	* @param value 
	*/
	public void setAsset_name(String value) {
		this.asset_name = value;
	}
	
	/**
	* 获取 资产名称
	* @return String
	*/
	public String getAsset_name() {
		return this.asset_name;
	}
	/**
	* 设置 资产类别编码
	* @param value 
	*/
	public void setAsset_type_code(String value) {
		this.asset_type_code = value;
	}
	
	/**
	* 获取 资产类别编码
	* @return String
	*/
	public String getAsset_type_code() {
		return this.asset_type_code;
	}
	/**
	* 设置 是否计提折旧
	* @param value 
	*/
	public void setIs_depre(Integer value) {
		this.is_depre = value;
	}
	
	/**
	* 获取 是否计提折旧
	* @return Integer
	*/
	public Integer getIs_depre() {
		return this.is_depre;
	}
	/**
	* 设置 折旧年限
	* @param value 
	*/
	public void setDepre_year(Long value) {
		this.depre_year = value;
	}
	
	/**
	* 获取 折旧年限
	* @return Long
	*/
	public Long getDepre_year() {
		return this.depre_year;
	}
	/**
	* 设置 0:不停用 1:停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0:不停用 1:停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
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