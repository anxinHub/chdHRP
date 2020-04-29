/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 工资明细数据与工资项关系表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostWageDetailMap implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 工资项编码
	 */
	private String wage_code;
	/**
	 * 工资明细列
	 */
	private String wage_column;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	/**
	 * 设置 ID
	 */
	public void setId(Long value) {
		this.id = value;
	}
	/**
	 * 获取 ID
	 */	
	public Long getId() {
		return this.id;
	}
	/**
	 * 设置 工资项编码
	 */
	public void setWage_code(String value) {
		this.wage_code = value;
	}
	/**
	 * 获取 工资项编码
	 */	
	public String getWage_code() {
		return this.wage_code;
	}
	/**
	 * 设置 工资明细列
	 */
	public void setWage_column(String value) {
		this.wage_column = value;
	}
	/**
	 * 获取 工资明细列
	 */	
	public String getWage_column() {
		return this.wage_column;
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