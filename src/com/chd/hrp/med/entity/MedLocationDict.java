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
 * 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
 * @Table:
 * MED_LOCATION_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedLocationDict implements Serializable {

	
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
	 * 货位ID
	 */
	private Long location_id;
	
	/**
	 * 货位编码
	 */
	private String location_code;
	
	/**
	 * 货位名称
	 */
	private String location_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 排位编号
	 */
	private String grid_no;
	
	/**
	 * 货位分类ID
	 */
	private Long location_type_id;
	/**
	 * 货位分类code
	 */
	private String location_type_code;
	
	
	private Long location_store_id;
	/**
	 * 所属库房ID
	 */
	private String store_id;
	
	/**
	 * 所属库房code
	 */
	private String store_code;
	
	/**
	 * 所属库房name
	 */
	private String store_name;
	
	
	/**
	 * 货位性质
	 */
	private String location_nature;
	
	/**
	 * 控制方式
	 */
	private String ctrl_type;
	
	/**
	 * 拣货员
	 */
	private String picker;
	
	private String picker_code;
	
	private String picker_name;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
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
	
	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 货位ID
	* @param value 
	*/
	public void setLocation_id(Long value) {
		this.location_id = value;
	}
	
	/**
	* 获取 货位ID
	* @return Long
	*/
	public Long getLocation_id() {
		return this.location_id;
	}
	/**
	* 设置 货位编码
	* @param value 
	*/
	public void setLocation_code(String value) {
		this.location_code = value;
	}
	
	/**
	* 获取 货位编码
	* @return String
	*/
	public String getLocation_code() {
		return this.location_code;
	}
	/**
	* 设置 货位名称
	* @param value 
	*/
	public void setLocation_name(String value) {
		this.location_name = value;
	}
	
	/**
	* 获取 货位名称
	* @return String
	*/
	public String getLocation_name() {
		return this.location_name;
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
	* 设置 排位编号
	* @param value 
	*/
	public void setGrid_no(String value) {
		this.grid_no = value;
	}
	
	public String getPicker_code() {
		return picker_code;
	}

	public void setPicker_code(String picker_code) {
		this.picker_code = picker_code;
	}

	/**
	* 获取 排位编号
	* @return String
	*/
	public String getGrid_no() {
		return this.grid_no;
	}
	/**
	* 设置 货位分类ID
	* @param value 
	*/
	public void setLocation_type_id(Long value) {
		this.location_type_id = value;
	}
	
	/**
	* 获取 货位分类ID
	* @return Long
	*/
	public Long getLocation_type_id() {
		return this.location_type_id;
	}
	/**
	* 设置 所属库房ID
	* @param value 
	*/
	public void setStore_id(String value) {
		this.store_id = value;
	}
	
	/**
	* 获取 所属库房ID
	* @return String
	*/
	public String getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 货位性质
	* @param value 
	*/
	public void setLocation_nature(String value) {
		this.location_nature = value;
	}
	
	/**
	* 获取 货位性质
	* @return String
	*/
	public String getLocation_nature() {
		return this.location_nature;
	}
	/**
	* 设置 控制方式
	* @param value 
	*/
	public void setCtrl_type(String value) {
		this.ctrl_type = value;
	}
	
	/**
	* 获取 控制方式
	* @return String
	*/
	public String getCtrl_type() {
		return this.ctrl_type;
	}
	/**
	* 设置 拣货员
	* @param value 
	*/
	public void setPicker(String value) {
		this.picker = value;
	}
	
	/**
	* 获取 拣货员
	* @return String
	*/
	public String getPicker() {
		return this.picker;
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

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getLocation_type_code() {
		return location_type_code;
	}

	public void setLocation_type_code(String location_type_code) {
		this.location_type_code = location_type_code;
	}

	public String getPicker_name() {
		return picker_name;
	}

	public void setPicker_name(String picker_name) {
		this.picker_name = picker_name;
	}

	public Long getLocation_store_id() {
		return location_store_id;
	}

	public void setLocation_store_id(Long location_store_id) {
		this.location_store_id = location_store_id;
	}
	
	
	
	
}