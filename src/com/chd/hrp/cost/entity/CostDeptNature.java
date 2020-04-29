/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本习性(01 固定 02 变动)

<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostDeptNature implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private Integer nature_id;

	/**
	 * 成本习性编码
	 */
	private String nature_code;
	/**
	 * 成本习性名称
	 */
	private String nature_name;
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
	
	public Integer getNature_id() {
		return nature_id;
	}
	public void setNature_id(Integer nature_id) {
		this.nature_id = nature_id;
	}
	/**
	 * 设置 成本习性编码
	 */
	public void setNature_code(String value) {
		this.nature_code = value;
	}
	/**
	 * 获取 成本习性编码
	 */	
	public String getNature_code() {
		return this.nature_code;
	}
	/**
	 * 设置 成本习性名称
	 */
	public void setNature_name(String value) {
		this.nature_name = value;
	}
	/**
	 * 获取 成本习性名称
	 */	
	public String getNature_name() {
		return this.nature_name;
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
}