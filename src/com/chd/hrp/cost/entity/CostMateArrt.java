/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_材料信息字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostMateArrt implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;


	private String mate_type_code;

	private String mate_type_name;


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
	 * 材料分类ID
	 */
	private Long mate_type_id;
	/**
	 * 材料ID
	 */
	private Long mate_id;
	/**
	 * 材料编码
	 */
	private String mate_code;
	/**
	 * 材料名称
	 */
	private String mate_name;
	/**
	 * 型号
	 */
	private String mate_mode;
	/**
	 * 单位
	 */
	private String meas_code;
	/**
	 * 单价
	 */
	private double price;
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
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	 * 设置 材料分类ID
	 */
	public void setMate_type_id(Long value) {
		this.mate_type_id = value;
	}
	/**
	 * 获取 材料分类ID
	 */	
	public Long getMate_type_id() {
		return this.mate_type_id;
	}
	/**
	 * 设置 材料ID
	 */
	public void setMate_id(Long value) {
		this.mate_id = value;
	}
	/**
	 * 获取 材料ID
	 */	
	public Long getMate_id() {
		return this.mate_id;
	}
	/**
	 * 设置 材料编码
	 */
	public void setMate_code(String value) {
		this.mate_code = value;
	}
	/**
	 * 获取 材料编码
	 */	
	public String getMate_code() {
		return this.mate_code;
	}
	/**
	 * 设置 材料名称
	 */
	public void setMate_name(String value) {
		this.mate_name = value;
	}
	/**
	 * 获取 材料名称
	 */	
	public String getMate_name() {
		return this.mate_name;
	}
	/**
	 * 设置 型号
	 */
	public void setMate_mode(String value) {
		this.mate_mode = value;
	}
	/**
	 * 获取 型号
	 */	
	public String getMate_mode() {
		return this.mate_mode;
	}
	/**
	 * 设置 单位
	 */
	public void setMeas_code(String value) {
		this.meas_code = value;
	}
	/**
	 * 获取 单位
	 */	
	public String getMeas_code() {
		return this.meas_code;
	}
	/**
	 * 设置 单价
	 */
	public void setPrice(double value) {
		this.price = value;
	}
	/**
	 * 获取 单价
	 */	
	public double getPrice() {
		return this.price;
	}
	/**
	 * 设置 拼音码
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	/**
	 * 获取 拼音码
	 */	
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	/**
	 * 获取 五笔码
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
	
    public String getMate_type_code() {
    	return mate_type_code;
    }
	
    public void setMate_type_code(String mate_type_code) {
    	this.mate_type_code = mate_type_code;
    }
	
    public String getMate_type_name() {
    	return mate_type_name;
    }
	
    public void setMate_type_name(String mate_type_name) {
    	this.mate_type_name = mate_type_name;
    }
	
}