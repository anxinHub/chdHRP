/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 系统参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccPara implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 参数编码
	 */
	private String para_code;
	/**
	 * 参数名称
	 */
	private String para_name;
	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 模块编码
	 */
	private String mod_code;
	/**
	 * 0文本框，1下拉框
	 */
	private Integer para_type;
	/**
	 * 参数选项
	 */
	private String para_json;
	/**
	 * 参数值
	 */
	private String para_value;
	/**
	 * 参数说明
	 */
	private String note;
	/**
	 * 是否停用
	 */
	private Integer is_stop;

	/**
	 * 设置 参数编码
	 */
	public void setPara_code(String value) {
		this.para_code = value;
	}
	/**
	 * 获取 参数编码
	 */	
	public String getPara_code() {
		return this.para_code;
	}
	/**
	 * 设置 参数名称
	 */
	public void setPara_name(String value) {
		this.para_name = value;
	}
	/**
	 * 获取 参数名称
	 */	
	public String getPara_name() {
		return this.para_name;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(double value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public double getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(double value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public double getHos_id() {
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
	 * 设置 模块编码
	 */
	public void setMod_code(String value) {
		this.mod_code = value;
	}
	/**
	 * 获取 模块编码
	 */	
	public String getMod_code() {
		return this.mod_code;
	}
	/**
	 * 设置 0文本框，1下拉框
	 */
	public void setPara_type(Integer value) {
		this.para_type = value;
	}
	/**
	 * 获取 0文本框，1下拉框
	 */	
	public Integer getPara_type() {
		return this.para_type;
	}
	/**
	 * 设置 参数选项
	 */
	public void setPara_json(String value) {
		this.para_json = value;
	}
	/**
	 * 获取 参数选项
	 */	
	public String getPara_json() {
		return this.para_json;
	}
	/**
	 * 设置 参数值
	 */
	public void setPara_value(String value) {
		this.para_value = value;
	}
	/**
	 * 获取 参数值
	 */	
	public String getPara_value() {
		return this.para_value;
	}
	/**
	 * 设置 参数说明
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 参数说明
	 */	
	public String getNote() {
		return this.note;
	}
	/**
	 * 设置 是否停用
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 是否停用
	 */	
	public Integer getIs_stop() {
		return this.is_stop;
	}
}