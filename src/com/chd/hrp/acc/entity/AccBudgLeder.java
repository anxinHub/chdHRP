/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 坏账提取表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccBudgLeder implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 计提ID
	 */
	private Long range_id;
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
	 * 坏账提取科目
	 */
	private String subj_code_hz;
	/**
	 * 管理费用科目
	 */
	private String subj_code_gl;
	/**
	 * 固定计提比例
	 */
	private double range_pro;
	/**
	 * 0余额百分比法、1账龄分析法
	 */
	private Integer range_type;
	
	
	private String vouch_date;
	
	private String vouch_code;
	
	private double end_os;
	
	private double hz_end_os;
	
	private double hz_os;
	
	private String subj_name_hz;
	
	private String subj_name_gl;
	
  
	
	public String getVouch_date() {
		return vouch_date;
	}
	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}
	public String getVouch_code() {
		return vouch_code;
	}
	public void setVouch_code(String vouch_code) {
		this.vouch_code = vouch_code;
	}
	public double getEnd_os() {
		return end_os;
	}
	public void setEnd_os(double end_os) {
		this.end_os = end_os;
	}
	public double getHz_end_os() {
		return hz_end_os;
	}
	public void setHz_end_os(double hz_end_os) {
		this.hz_end_os = hz_end_os;
	}
	public double getHz_os() {
		return hz_os;
	}
	public void setHz_os(double hz_os) {
		this.hz_os = hz_os;
	}
	public String getSubj_name_hz() {
		return subj_name_hz;
	}
	public void setSubj_name_hz(String subj_name_hz) {
		this.subj_name_hz = subj_name_hz;
	}
	public String getSubj_name_gl() {
		return subj_name_gl;
	}
	public void setSubj_name_gl(String subj_name_gl) {
		this.subj_name_gl = subj_name_gl;
	}
	/**
	 * 设置 计提ID
	 */
	public void setRange_id(Long value) {
		this.range_id = value;
	}
	/**
	 * 获取 计提ID
	 */	
	public Long getRange_id() {
		return this.range_id;
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
	
	public String getSubj_code_hz() {
		return subj_code_hz;
	}
	public void setSubj_id_hz(String subj_code_hz) {
		this.subj_code_hz = subj_code_hz;
	}
	public String getSubj_code_gl() {
		return subj_code_gl;
	}
	public void setSubj_code_gl(String subj_code_gl) {
		this.subj_code_gl = subj_code_gl;
	}
	/**
	 * 设置 固定计提比例
	 */
	public void setRange_pro(double value) {
		this.range_pro = value;
	}
	/**
	 * 获取 固定计提比例
	 */	
	public double getRange_pro() {
		return this.range_pro;
	}
	/**
	 * 设置 0余额百分比法、1账龄分析法
	 */
	public void setRange_type(Integer value) {
		this.range_type = value;
	}
	/**
	 * 获取 0余额百分比法、1账龄分析法
	 */	
	public Integer getRange_type() {
		return this.range_type;
	}
}