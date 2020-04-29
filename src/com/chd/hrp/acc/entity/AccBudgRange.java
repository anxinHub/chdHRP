/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 账龄区间表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccBudgRange implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 计提ID
	 */
	private double range_id;
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
	 * 起始天数
	 */
	private double begin_days;
	/**
	 * 终止天数
	 */
	private double end_days;
	/**
	 * 计提比例
	 */
	private double range_pro;
	/**
	 * 区间描述
	 */
	private String note;

	/**
	 * 设置 计提ID
	 */
	public void setRange_id(double value) {
		this.range_id = value;
	}
	/**
	 * 获取 计提ID
	 */	
	public double getRange_id() {
		return this.range_id;
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
	 * 设置 起始天数
	 */
	public void setBegin_days(double value) {
		this.begin_days = value;
	}
	/**
	 * 获取 起始天数
	 */	
	public double getBegin_days() {
		return this.begin_days;
	}
	/**
	 * 设置 终止天数
	 */
	public void setEnd_days(double value) {
		this.end_days = value;
	}
	/**
	 * 获取 终止天数
	 */	
	public double getEnd_days() {
		return this.end_days;
	}
	/**
	 * 设置 计提比例
	 */
	public void setRange_pro(double value) {
		this.range_pro = value;
	}
	/**
	 * 获取 计提比例
	 */	
	public double getRange_pro() {
		return this.range_pro;
	}
	/**
	 * 设置 区间描述
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 区间描述
	 */	
	public String getNote() {
		return this.note;
	}
}