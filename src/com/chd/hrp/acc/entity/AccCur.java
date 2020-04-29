/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 币种<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCur implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 币种编码
	 */
	private String cur_code;
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
	 * 年度
	 */
	private String acc_year;
	/**
	 * 币种名称
	 */
	private String cur_name;
	/**
	 * 汇率小数位
	 */
	private Integer cur_num;
	/**
	 * 期间汇率
	 */
	private double cur_rate;
	/**
	 * 0原币*汇率 = 本位币，1原币/汇率 = 本位币
	 */
	private Integer cur_plan;
	/**
	 * 是否本币
	 */
	private Integer is_self;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 是否停用
	 */
	private Integer is_stop;

	/**
	 * 设置 币种编码
	 */
	public void setCur_code(String value) {
		this.cur_code = value;
	}
	/**
	 * 获取 币种编码
	 */	
	public String getCur_code() {
		return this.cur_code;
	}
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
	 * 设置 年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 币种名称
	 */
	public void setCur_name(String value) {
		this.cur_name = value;
	}
	/**
	 * 获取 币种名称
	 */	
	public String getCur_name() {
		return this.cur_name;
	}
	/**
	 * 设置 汇率小数位
	 */
	public void setCur_num(Integer value) {
		this.cur_num = value;
	}
	/**
	 * 获取 汇率小数位
	 */	
	public Integer getCur_num() {
		return this.cur_num;
	}
	/**
	 * 设置 期间汇率
	 */
	public void setCur_rate(double value) {
		this.cur_rate = value;
	}
	/**
	 * 获取 期间汇率
	 */	
	public double getCur_rate() {
		return this.cur_rate;
	}
	/**
	 * 设置 0原币*汇率 = 本位币，1原币/汇率 = 本位币
	 */
	public void setCur_plan(Integer value) {
		this.cur_plan = value;
	}
	/**
	 * 获取 0原币*汇率 = 本位币，1原币/汇率 = 本位币
	 */	
	public Integer getCur_plan() {
		return this.cur_plan;
	}
	/**
	 * 设置 是否本币
	 */
	public void setIs_self(Integer value) {
		this.is_self = value;
	}
	/**
	 * 获取 是否本币
	 */	
	public Integer getIs_self() {
		return this.is_self;
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