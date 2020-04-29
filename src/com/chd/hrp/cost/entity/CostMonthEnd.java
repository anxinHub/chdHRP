/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室成本核算月结表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostMonthEnd implements Serializable {

	
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
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 会计期间
	 */
	private String acc_month;
	/**
	 * 是否月结
	 */
	private Integer is_end;
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
	 * 设置 会计年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 会计年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 会计期间
	 */
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	/**
	 * 获取 会计期间
	 */	
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	 * 设置 是否月结
	 */
	public void setIs_end(Integer value) {
		this.is_end = value;
	}
	/**
	 * 获取 是否月结
	 */	
	public Integer getIs_end() {
		return this.is_end;
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